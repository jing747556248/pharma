package com.sanofi.pharma.core.repository;

import com.sanofi.pharma.core.entity.PharmacyDrugRelationship;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

@Repository
public interface PharmacyDrugRelationshipRepository extends JpaRepository<PharmacyDrugRelationship, Long>, JpaSpecificationExecutor<PharmacyDrugRelationship> {

    /**
     * 通过药房id和药品id查询
     */
    PharmacyDrugRelationship findByPharmacyIdAndDrugId(Long pharmacyId, Long drugId);

//    /**
//     * 批量更新库存
//     */
//    @Modifying
//    @Query(nativeQuery = true)
//    @Transactional // 使用动态SQL占位符
//    int batchUpdateStocks(@Param("sql") String sql);
}
