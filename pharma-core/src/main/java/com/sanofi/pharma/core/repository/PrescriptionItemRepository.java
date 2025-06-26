package com.sanofi.pharma.core.repository;

import com.sanofi.pharma.core.entity.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {

    /**
     * 通过处方id查询处方明细
     */
    List<PrescriptionItem> findByPrescriptionIdAndIsDeleted(Long prescriptionId, Boolean isDeleted);
}
