package com.sanofi.pharma.core.entity.check;

import com.sanofi.pharma.common.dto.RespCode;
import com.sanofi.pharma.common.exception.BizException;
import com.sanofi.pharma.core.entity.Drug;
import com.sanofi.pharma.core.entity.Pharmacy;
import com.sanofi.pharma.core.entity.PharmacyDrugRelationship;
import com.sanofi.pharma.core.repository.DrugRepository;
import com.sanofi.pharma.core.repository.PharmacyRepository;
import com.sanofi.pharma.core.util.DateUtil;
import com.yahoo.elide.annotation.SecurityCheck;
import com.yahoo.elide.core.security.ChangeSpec;
import com.yahoo.elide.core.security.RequestScope;
import com.yahoo.elide.core.security.checks.OperationCheck;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Component
@SecurityCheck(PharmacyDrugRelationshipCreateCheck.CREATE_CHECK)
@RequiredArgsConstructor
public class PharmacyDrugRelationshipCreateCheck extends OperationCheck<PharmacyDrugRelationship> {

    @Resource
    @Lazy
    private DrugRepository drugRepository;

    @Resource
    @Lazy
    private final PharmacyRepository pharmacyRepository;

    public static final String CREATE_CHECK = "CREATE CHECK";

    @Override
    public boolean ok(PharmacyDrugRelationship relationship, RequestScope requestScope, Optional<ChangeSpec> changeSpec) {
        // 检查药品是否存在
        Optional<Drug> drugOptional = drugRepository.findById(relationship.getDrugId());
        if (drugOptional.isEmpty()) {
            throw new BizException(RespCode.DRUG_NOT_EXIST);
        }
        // 检查药品expiryDate
        Drug drug = drugOptional.get();
        LocalDate now = LocalDate.now();
        Date expiryDate = DateUtil.stringToDate(drug.getExpiryDate(), DateUtil.DATE_FORMAT_YEAR_MONTH_DAY2);
        LocalDate expiryLocalDate = DateUtil.dateToLocalDate(expiryDate);
        if (now.isAfter(expiryLocalDate)) {
            throw new BizException(RespCode.DRUG_HAS_EXPIRED);
        }
        // 检查药房是否存在
        Optional<Pharmacy> pharmacyOptional = pharmacyRepository.findById(relationship.getPharmacyId());
        if (pharmacyOptional.isEmpty()) {
            throw new BizException(RespCode.PHARMACY_NOT_EXIST);
        }

        return true;
    }
}
