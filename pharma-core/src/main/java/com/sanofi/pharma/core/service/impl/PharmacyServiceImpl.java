package com.sanofi.pharma.core.service.impl;

import com.sanofi.pharma.core.entity.Drug;
import com.sanofi.pharma.core.entity.PharmacyDrugRelationship;
import com.sanofi.pharma.core.repository.DrugRepository;
import com.sanofi.pharma.core.repository.PharmacyDrugRelationshipRepository;
import com.sanofi.pharma.core.repository.Specification.PharmacyDrugRelationshipSpecification;
import com.sanofi.pharma.core.service.PharmacyService;
import com.sanofi.pharma.core.vo.DrugInPharmacyVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 药房信息服务类
 *
 * @author lijin
 * @since 2025-06-16
 */
@Slf4j
@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Resource
    private PharmacyDrugRelationshipRepository pharmacyDrugRelationshipRepository;

    @Resource
    private DrugRepository drugRepository;

    @Override
    public List<DrugInPharmacyVO> getDrugsByPharmacyId(Long pharmacyId, Integer pageNum, Integer pageSize) {
        // define return instance
        List<DrugInPharmacyVO> returnList = new ArrayList<>();

        // query pharmacy drug relationship list by page
        Specification<PharmacyDrugRelationship> specification = Specification
                .where(PharmacyDrugRelationshipSpecification.filter(pharmacyId));

        Sort sort = Sort.by(Sort.Order.asc("drugId"));
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<PharmacyDrugRelationship> relationshipByPage = pharmacyDrugRelationshipRepository.findAll(specification, pageable);

        // query drug list by drug id
        if (CollectionUtils.isNotEmpty(relationshipByPage.getContent())) {
            List<Long> drugIdList = relationshipByPage.getContent().stream().map(PharmacyDrugRelationship::getDrugId).toList();
            // Under this pharmacy, the stock of each drug
            Map<Long, Integer> drugStockMap = relationshipByPage.stream().collect(Collectors.toMap(PharmacyDrugRelationship::getDrugId, PharmacyDrugRelationship::getStock));
            List<Drug> drugList = drugRepository.findByIdIn(drugIdList, Boolean.FALSE);
            if (CollectionUtils.isNotEmpty(drugList)) {
                drugList.forEach(i -> {
                    DrugInPharmacyVO drugInPharmacyVO = new DrugInPharmacyVO();
                    drugInPharmacyVO.setDrugId(i.getId());
                    drugInPharmacyVO.setStock(drugStockMap.get(i.getId()));
                    drugInPharmacyVO.setName(i.getName());
                    drugInPharmacyVO.setManufacturer(i.getManufacturer());
                    drugInPharmacyVO.setBatchNumber(i.getBatchNumber());
                    drugInPharmacyVO.setProductionDate(i.getProductionDate());
                    drugInPharmacyVO.setExpiryDate(i.getExpiryDate());
                    returnList.add(drugInPharmacyVO);
                });
            }
        }
        return returnList;
    }
}
