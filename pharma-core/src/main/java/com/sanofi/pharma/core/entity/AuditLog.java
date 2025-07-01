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
@Schema(description = "audit log table")
public class AuditLog extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "primary key id")
    private Long id;

    /**
     * prescription id
     */
    @Schema(description = "prescription id")
    private Long prescriptionId;

    /**
     * patient id
     */
    @Schema(description = "patient id")
    private Long patientId;

    /**
     * pharmacy id
     */
    @Schema(description = "pharmacy id")
    private Long pharmacyId;

    /**
     * required drugs json
     */
    @Schema(description = "required drug list json")
    private String requiredDrugs;

    /**
     * Dispensed status (1: successful dispensed, 2: failed dispensed)
     */
    @Schema(description = "Dispensed status (1: successful dispensed, 2: failed dispensed)")
    private Integer dispensedStatus;

    /**
     * failure reason
     */
    @Schema(description = "failure reason")
    private String failureReason;

}
