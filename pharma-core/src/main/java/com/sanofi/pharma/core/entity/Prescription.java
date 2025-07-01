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
 * 处方主表
 * </p>
 *
 * @author lijin
 * @since 2025-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Include(name = "prescription")
@Schema(description = "prescription table")
public class Prescription extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "primary key id")
    private Long id;

    /**
     * pharmacy id
     */
    @Schema(description = "pharmacy id")
    private Long pharmacyId;

    /**
     * patient id
     */
    @Schema(description = "patient id")
    private Long patientId;

    /**
     * status (0: Not executed, 1: drug dispensed successfully, 2: drug dispensed failed)
     */
    @Schema(description = "status (0: Not executed, 1: drug dispensed successfully, 2: drug dispensed failed)")
    private Integer status;
}
