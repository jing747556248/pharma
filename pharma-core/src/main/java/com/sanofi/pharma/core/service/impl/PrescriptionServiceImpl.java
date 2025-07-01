package com.sanofi.pharma.core.service.impl;

import com.alibaba.fastjson2.JSON;
import com.sanofi.pharma.common.dto.RespCode;
import com.sanofi.pharma.common.exception.BizException;
import com.sanofi.pharma.core.constant.CommonConstant;
import com.sanofi.pharma.core.dto.FulfillPrescriptionRequestDTO;
import com.sanofi.pharma.core.dto.PrescriptionDTO;
import com.sanofi.pharma.core.dto.PrescriptionItemDTO;
import com.sanofi.pharma.core.entity.PharmacyDrugRelationship;
import com.sanofi.pharma.core.entity.Prescription;
import com.sanofi.pharma.core.entity.PrescriptionItem;
import com.sanofi.pharma.core.enums.PrescriptionEventTypeEnum;
import com.sanofi.pharma.core.enums.PrescriptionStatusEnum;
import com.sanofi.pharma.core.event.PrescriptionEvent;
import com.sanofi.pharma.core.repository.DrugRepository;
import com.sanofi.pharma.core.repository.PharmacyDrugRelationshipRepository;
import com.sanofi.pharma.core.repository.PrescriptionItemRepository;
import com.sanofi.pharma.core.repository.PrescriptionRepository;
import com.sanofi.pharma.core.service.PrescriptionService;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 处方信息服务类
 *
 * @author lijin
 * @since 2025-06-19
 */
@Slf4j
@Service
public class PrescriptionServiceImpl implements PrescriptionService, ApplicationEventPublisherAware {

    @Resource
    private PrescriptionRepository prescriptionRepository;

    @Resource
    private PrescriptionItemRepository prescriptionItemRepository;

    @Resource
    private PharmacyDrugRelationshipRepository pharmacyDrugRelationshipRepository;

    @Resource
    private EntityManager entityManager;

    private ApplicationEventPublisher applicationEventPublisher = null;

    /**
     * create prescription
     *
     */
    @Override
    @Transactional
    public Prescription createPrescription(PrescriptionDTO request) {
        // 参数校验 check param
        this.checkParam(request);

        // 保存处方基本信息 save prescription base info
        Prescription prescription = prescriptionRepository.save(this.buildPrescription(request));

        // 保存处方明细 save prescription item info
        List<PrescriptionItem> prescriptionItemList = prescriptionItemRepository.saveAll(this.buildPrescriptionItemList(request, prescription.getId()));

        // 记录审计日志 record audit log
        applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescription, prescriptionItemList, "", PrescriptionEventTypeEnum.CREATE.toString()));
        return prescription;
    }

    /**
     * fulfill prescription
     */
    @Retryable(
            retryFor = {OptimisticLockException.class},
            noRetryFor = {BizException.class},
            maxAttempts = 3, // Maximum number of retries
            backoff = @Backoff(delay = 500) // delay 500ms every retry
    )
    @Override
    @Transactional
    public Boolean fulfillPrescription(FulfillPrescriptionRequestDTO request) {
        // check parma
        Prescription prescription = this.checkParam(request);

        // query prescription item
        List<PrescriptionItem> prescriptionItemList = prescriptionItemRepository.findByPrescriptionIdAndIsDeleted(prescription.getId(), false);
        Map<Long, Integer> itemMap = prescriptionItemList.stream().collect(Collectors.toMap(PrescriptionItem::getDrugId, PrescriptionItem::getQuantity));

        // query the drugs included in the prescription
        List<PharmacyDrugRelationship> pharmacyDrugRelationshipList = pharmacyDrugRelationshipRepository.findByPharmacyIdAndDrugIdIn(prescription.getPharmacyId(), prescriptionItemList.stream().map(PrescriptionItem::getDrugId).toList());

        // determine if the stock is enough
        if (pharmacyDrugRelationshipList.stream().anyMatch(stock -> itemMap.get(stock.getDrugId()) > stock.getStock())) {
            // record audit log
            applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescription, prescriptionItemList, RespCode.PRESCRIPTION_DRUG_QUANTITY_NOT_ENOUGH.getDetail(), PrescriptionEventTypeEnum.FULFILL_FAIL.toString()));
            throw new BizException(RespCode.PRESCRIPTION_DRUG_QUANTITY_NOT_ENOUGH); // 库存不足
        }

        // batch update pharmacy stock with optimistic lock
        pharmacyDrugRelationshipList.stream().forEach(p -> {
            p.setStock(p.getStock() - itemMap.get(p.getDrugId()));
            p.setUpdateTime(new Date());
        });
        pharmacyDrugRelationshipRepository.saveAll(pharmacyDrugRelationshipList);

        // update prescription status
        prescription.setStatus(PrescriptionStatusEnum.FULFILL_SUCCESS.getCode());
        prescription.setUpdateTime(new Date());
        prescriptionRepository.save(prescription);

        // record audit log
        applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescription, prescriptionItemList, "", PrescriptionEventTypeEnum.FULFILL_SUCCESS.toString()));
        return Boolean.TRUE;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * check param for creating prescription
     */
    private void checkParam(PrescriptionDTO request) {
        log.info("create prescription request: {}", JSON.toJSONString(request));
        // Prescription Drug List is Empty
        if (CollectionUtils.isEmpty(request.getItemDTOList())) {
            log.info("create prescription exception: 处方中没有药品");
            throw new BizException(RespCode.PRESCRIPTION_DRUG_LIST_EMPTY);
        }
        // 校验处方中药品是否充足 Verify whether the drugs in the prescription are sufficient
        request.getItemDTOList().forEach(itemDTO -> {
            PharmacyDrugRelationship pharmacyDrugRelationship = pharmacyDrugRelationshipRepository.findByPharmacyIdAndDrugId(request.getPharmacyId(), itemDTO.getDrugId());
            if (pharmacyDrugRelationship == null) {
                log.info("create prescription exception: 药房没有处方中的药品");
                throw new BizException(RespCode.PRESCRIPTION_DRUG_NOT_EXIST);
            }
            // 处方的药品数量>药品在药房的库存量
            if (itemDTO.getQuantity() > pharmacyDrugRelationship.getStock()) {
                log.info("create prescription exception: 药房的药品库存量不足");
                throw new BizException(RespCode.PRESCRIPTION_DRUG_QUANTITY_NOT_ENOUGH);
            }
        });
    }

    /**
     * check param for fulfilling prescription
     */
    private Prescription checkParam(FulfillPrescriptionRequestDTO request) {
        log.info("fulfill prescription request: {}", JSON.toJSONString(request));
        // Pessimistic lock, avoid dirty reading
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findWithLockById(request.getPrescriptionId());
        if (prescriptionOptional.isEmpty()) {
            log.info("create prescription exception: 处方不存在");
            throw new BizException(RespCode.PRESCRIPTION_NOT_EXIST);
        }
        Prescription prescription = prescriptionOptional.get();
        if (!prescription.getStatus().equals(PrescriptionStatusEnum.NOT_FULFILL.getCode())) {
            log.info("create prescription exception: 处方已经执行过了");
            throw new BizException(RespCode.PRESCRIPTION_HAS_BEEN_FULFILLED);
        }
        return prescription;
    }

    private Prescription buildPrescription(PrescriptionDTO request) {
        Prescription prescription = new Prescription();
        prescription.setPharmacyId(request.getPharmacyId());
        prescription.setPatientId(request.getPatientId());
        prescription.setStatus(PrescriptionStatusEnum.NOT_FULFILL.getCode());
        prescription.setCreateBy(CommonConstant.SYSTEM_ADMIN);
        prescription.setCreateTime(new Date());
        prescription.setUpdateBy(CommonConstant.SYSTEM_ADMIN);
        prescription.setUpdateTime(new Date());
        prescription.setIsDeleted(false);
        return prescription;
    }

    private List<PrescriptionItem> buildPrescriptionItemList(PrescriptionDTO request, Long prescriptionId) {
        List<PrescriptionItem> prescriptionItemList = new ArrayList<>();
        request.getItemDTOList().forEach(itemDTO -> {
            PrescriptionItem prescriptionItem = new PrescriptionItem();
            prescriptionItem.setPrescriptionId(prescriptionId);
            prescriptionItem.setDrugId(itemDTO.getDrugId());
            prescriptionItem.setQuantity(itemDTO.getQuantity());
            prescriptionItem.setDosage(itemDTO.getDosage());
            prescriptionItem.setCreateBy(CommonConstant.SYSTEM_ADMIN);
            prescriptionItem.setCreateTime(new Date());
            prescriptionItem.setUpdateBy(CommonConstant.SYSTEM_ADMIN);
            prescriptionItem.setUpdateTime(new Date());
            prescriptionItem.setIsDeleted(false);
            prescriptionItemList.add(prescriptionItem);
        });
        return prescriptionItemList;
    }

    private void batchUpdateStock(List<PharmacyDrugRelationship> pharmacyDrugRelationshipList, Map<Long, Integer> itemMap) {
        List<Long> ids = pharmacyDrugRelationshipList.stream().map(PharmacyDrugRelationship::getId).toList();
        // 动态构建SQL
        StringBuilder sqlBuilder = new StringBuilder(
                "UPDATE pharmacy_drug_relationship SET stock = CASE id "
        );
        pharmacyDrugRelationshipList.forEach(p -> {
            sqlBuilder.append("WHEN ")
                    .append(p.getId())
                    .append(" THEN ")
                    .append(p.getStock() - itemMap.get(p.getDrugId()))
                    .append(" ");
        });
        sqlBuilder.append("END WHERE id IN :ids");

        // 执行更新
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Recover
    public Boolean recoverAfterFulfillPrescriptionFail(OptimisticLockException e, FulfillPrescriptionRequestDTO request) {
        log.error("乐观锁重试5次后仍失败，request: {}", request);
        // record audit log
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(request.getPrescriptionId());
        List<PrescriptionItem> prescriptionItemList = prescriptionItemRepository.findByPrescriptionIdAndIsDeleted(prescriptionOptional.isPresent() ? prescriptionOptional.get().getId() : -1, false);
        applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescriptionOptional.orElse(null), prescriptionItemList, "系统繁忙，多次尝试扣减药房库存失败", PrescriptionEventTypeEnum.FULFILL_FAIL.toString()));
        throw new BizException(RespCode.SYSTEM_BUSY);
    }

    @Recover
    public Boolean recoverAfterFulfillPrescriptionFail(BizException e, FulfillPrescriptionRequestDTO request) {
        // record audit log
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(request.getPrescriptionId());
        List<PrescriptionItem> prescriptionItemList = prescriptionItemRepository.findByPrescriptionIdAndIsDeleted(prescriptionOptional.isPresent() ? prescriptionOptional.get().getId() : -1, false);
        applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescriptionOptional.orElse(null), prescriptionItemList, e.getRespCode().getTitle(), PrescriptionEventTypeEnum.FULFILL_FAIL.toString()));
        throw e;
    }
}
