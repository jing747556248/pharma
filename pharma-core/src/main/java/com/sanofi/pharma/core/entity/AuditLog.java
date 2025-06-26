package com.sanofi.pharma.core.entity;

import com.yahoo.elide.annotation.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 处方审计日志表
 * </p>
 *
 * @author lijin
 * @since 2025-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Include(name = "auditLog")
@Schema(description = "处方审计日志表")
public class AuditLog extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 处方ID
     */
    @Schema(description = "处方ID")
    private Long prescriptionId;

    /**
     * 患者ID
     */
    @Schema(description = "患者ID")
    private Long patientId;

    /**
     * 药房ID
     */
    @Schema(description = "药房ID")
    private Long pharmacyId;

    /**
     * 所需药品JSON数组
     */
    @Schema(description = "所需药品JSON数组")
    private String requiredDrugs;

    /**
     * 配药状态(1:配药成功,2:配药失败)
     */
    @Schema(description = "配药状态(1:配药成功,2:配药失败)")
    private Integer dispensedStatus;

    /**
     * 失败原因
     */
    @Schema(description = "失败原因")
    private String failureReason;

}
