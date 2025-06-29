package com.sanofi.pharma.core.repository;

import com.sanofi.pharma.core.entity.AuditLog;
import com.sanofi.pharma.core.entity.Drug;
import com.sanofi.pharma.core.entity.PharmacyDrugRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, JpaSpecificationExecutor<AuditLog> {
}
