package com.sanofi.pharma.core.service;


import com.sanofi.pharma.core.entity.AuditLog;
import org.springframework.data.domain.Page;

/**
 * 审计日志服务类
 *
 * @author lijin
 * @since 2025-06-16
 */
public interface AuditLogService {

    Page<AuditLog> queryAuditLog(Long patientId, Long pharmacyId, Integer dispensedStatus, Integer pageNum, Integer pageSize);

}
