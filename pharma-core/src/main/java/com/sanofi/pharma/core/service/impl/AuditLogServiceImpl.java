package com.sanofi.pharma.core.service.impl;

import com.sanofi.pharma.core.entity.AuditLog;
import com.sanofi.pharma.core.repository.AuditLogRepository;
import com.sanofi.pharma.core.repository.Specification.AuditLogSpecification;
import com.sanofi.pharma.core.service.AuditLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


/**
 * 审计日志服务类
 *
 * @author lijin
 * @since 2025-06-16
 */
@Slf4j
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Resource
    private AuditLogRepository auditLogRepository;

    @Override
    public Page<AuditLog> queryAuditLog(Long patientId, Long pharmacyId, Integer dispensedStatus, Integer pageNum, Integer pageSize) {
        // 分页查询
        Specification<AuditLog> specification = Specification
                .where(AuditLogSpecification.filter(patientId, pharmacyId, dispensedStatus));

        Sort sort = Sort.by(Sort.Order.desc("id")); // id倒序
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<AuditLog> auditLogPage = auditLogRepository.findAll(specification, pageable);
        return auditLogPage;
    }
}
