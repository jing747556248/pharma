package com.sanofi.pharma.core.repository.Specification;

import com.sanofi.pharma.core.entity.AuditLog;
import com.sanofi.pharma.core.entity.PharmacyDrugRelationship;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AuditLogSpecification {

    public static final String PATIENT_ID = "patientId";
    public static final String PHARMACY_ID = "pharmacyId";
    public static final String DISPENSED_STATUS = "dispensedStatus";
    public static final String IS_DELETED = "isDeleted";

    public static Specification<AuditLog> filter(Long patientId, Long pharmacyId, Integer dispensedStatus) {
        return (Root<AuditLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (patientId != null) {
                predicateList.add(root.get(PATIENT_ID).in(patientId));
            }

            if (pharmacyId != null) {
                predicateList.add(root.get(PHARMACY_ID).in(pharmacyId));
            }

            if (dispensedStatus != null) {
                predicateList.add(root.get(DISPENSED_STATUS).in(dispensedStatus));
            }

            predicateList.add(root.get(IS_DELETED).in(Boolean.FALSE));

            if (CollectionUtils.isNotEmpty(predicateList)) {
                Predicate[] predicates = predicateList.toArray(Predicate[]::new);
                Predicate and = criteriaBuilder.and(predicates);
                return query.where(and).getRestriction();
            }
            return criteriaBuilder.conjunction();
        };
    }
}
