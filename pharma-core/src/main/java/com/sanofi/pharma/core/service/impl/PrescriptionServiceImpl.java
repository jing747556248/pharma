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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        // 校验处方是否存在 以及状态 check parma
        Prescription prescription = this.checkParam(request);

        // 扣减库存，更新处方状态 乐观锁  Deducting stock, updating prescription status with optimistic lock
        List<PrescriptionItem> prescriptionItemList = prescriptionItemRepository.findByPrescriptionIdAndIsDeleted(prescription.getId(), false);
        List<PharmacyDrugRelationship> pharmacyDrugRelationshipList = new ArrayList<>();
        boolean isEnough = true;
        for (PrescriptionItem prescriptionItem : prescriptionItemList) {
            // 查药房中该药品 query the drug in the pharmacy
            PharmacyDrugRelationship pharmacyDrugRelationship = pharmacyDrugRelationshipRepository.findByPharmacyIdAndDrugId(prescription.getPharmacyId(), prescriptionItem.getDrugId());
            // 判断库存是否够 determine if the stock is enough
            if (pharmacyDrugRelationship.getStock() < prescriptionItem.getQuantity()) {
                isEnough = false;
                break;
            }
            pharmacyDrugRelationship.setStock(pharmacyDrugRelationship.getStock() - prescriptionItem.getQuantity());
            pharmacyDrugRelationship.setUpdateTime(new Date());
            pharmacyDrugRelationshipList.add(pharmacyDrugRelationship);
        }

        if (isEnough) {
            // 批量更新药房库存 batch update pharmacy stock
            this.batchUpdateStock(pharmacyDrugRelationshipList);

            // 更新处方状态 update prescription status
            prescription.setStatus(PrescriptionStatusEnum.FULFILL_SUCCESS.getCode());
            prescriptionRepository.save(prescription);

            // 记录审计日志 record audit log
            applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescription, prescriptionItemList, "", PrescriptionEventTypeEnum.FULFILL_SUCCESS.toString()));
        } else {
            // 记录审计日志 record audit log
            applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescription, prescriptionItemList, RespCode.PRESCRIPTION_DRUG_QUANTITY_NOT_ENOUGH.getDetail(), PrescriptionEventTypeEnum.FULFILL_FAIL.toString()));
            throw new BizException(RespCode.PRESCRIPTION_DRUG_QUANTITY_NOT_ENOUGH);
        }
        return Boolean.TRUE;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 创建处方参数校验 check param for creating prescription
     */
    private void checkParam(PrescriptionDTO request) {
        log.info("create prescription request: {}", JSON.toJSONString(request));
        // 校验处方中是否包含药品 Prescription Drug List is Empty
        if (CollectionUtils.isEmpty(request.getItemDTOList())) {
            log.info("create prescription exception: 处方中没有药品");
            throw new BizException(RespCode.PRESCRIPTION_DRUG_LIST_EMPTY);
        }
        // 校验处方中药品是否充足 Verify whether the drugs in the prescription are sufficient
        for (PrescriptionItemDTO itemDTO : request.getItemDTOList()) {
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
        }
    }

    /**
     * 执行处方参数校验 check param for fulfilling prescription
     */
    private Prescription checkParam(FulfillPrescriptionRequestDTO request) {
        log.info("fulfill prescription request: {}", JSON.toJSONString(request));
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(request.getPrescriptionId());
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
        for (PrescriptionItemDTO itemDTO : request.getItemDTOList()) {
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
        }
        return prescriptionItemList;
    }

    private void batchUpdateStock(List<PharmacyDrugRelationship> pharmacyDrugRelationshipList) {
        List<Long> ids = pharmacyDrugRelationshipList.stream().map(PharmacyDrugRelationship::getId).toList();
        // 动态构建SQL
        StringBuilder sqlBuilder = new StringBuilder(
                "UPDATE pharmacy_drug_relationship SET stock = CASE id "
        );
        for (PharmacyDrugRelationship pharmacyDrugRelationship : pharmacyDrugRelationshipList) {
            sqlBuilder.append("WHEN ")
                    .append(pharmacyDrugRelationship.getId())
                    .append(" THEN ")
                    .append(pharmacyDrugRelationship.getStock())
                    .append(" ");
        }
        sqlBuilder.append("END WHERE id IN :ids");

        // 执行更新
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Recover
    public Boolean recoverAfterFulfillPrescriptionFail(OptimisticLockException e, FulfillPrescriptionRequestDTO request) {
        log.error("乐观锁重试5次后仍失败，request: {}", request);
        // 记录审计日志
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(request.getPrescriptionId());
        List<PrescriptionItem> prescriptionItemList = prescriptionItemRepository.findByPrescriptionIdAndIsDeleted(prescriptionOptional.isPresent() ? prescriptionOptional.get().getId() : -1, false);
        applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescriptionOptional.orElse(null), prescriptionItemList, "系统繁忙，多次尝试扣减药房库存失败", PrescriptionEventTypeEnum.FULFILL_FAIL.toString()));
        throw new BizException(RespCode.SYSTEM_BUSY);
    }

    @Recover
    public Boolean recoverAfterFulfillPrescriptionFail(BizException e, FulfillPrescriptionRequestDTO request) {
        // 记录审计日志
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(request.getPrescriptionId());
        List<PrescriptionItem> prescriptionItemList = prescriptionItemRepository.findByPrescriptionIdAndIsDeleted(prescriptionOptional.isPresent() ? prescriptionOptional.get().getId() : -1, false);
        applicationEventPublisher.publishEvent(new PrescriptionEvent(this, prescriptionOptional.orElse(null), prescriptionItemList, e.getRespCode().getDetail(), PrescriptionEventTypeEnum.FULFILL_FAIL.toString()));
        throw e;
    }
}
