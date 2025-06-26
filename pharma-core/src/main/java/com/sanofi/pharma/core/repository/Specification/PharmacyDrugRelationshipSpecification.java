package com.sanofi.pharma.core.repository.Specification;

import com.sanofi.pharma.core.entity.PharmacyDrugRelationship;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PharmacyDrugRelationshipSpecification {

    public static final String PHARMACY_ID = "pharmacyId";
    public static final String IS_DELETED = "isDeleted";

    public static Specification<PharmacyDrugRelationship> filter(Long pharmacyId) {
        return (Root<PharmacyDrugRelationship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(root.get(PHARMACY_ID).in(pharmacyId));
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
