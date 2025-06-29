package com.sanofi.pharma.core.controller;

import com.sanofi.pharma.common.dto.RespBody;
import com.sanofi.pharma.core.entity.AuditLog;
import com.sanofi.pharma.core.service.AuditLogService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Schema(description = "审计日志")
@RequestMapping("/api/audit-log")
@RestController
public class AuditLogController {

    @Resource
    private AuditLogService auditLogService;

    @Schema(description = "审计日志列表")
    @GetMapping
    public RespBody<Page<AuditLog>> queryAuditLog(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long pharmacyId,
            @RequestParam(required = false) Integer dispensedStatus,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return RespBody.ok(auditLogService.queryAuditLog(patientId, pharmacyId, dispensedStatus, pageNum, pageSize));
    }
}
