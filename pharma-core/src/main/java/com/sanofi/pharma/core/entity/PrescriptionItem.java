package com.sanofi.pharma.core.entity;

import com.yahoo.elide.annotation.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 处方明细表
 * </p>
 *
 * @author lijin
 * @since 2025-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Include(name = "prescriptionItem")
@Schema(description = "prescription item table")
public class PrescriptionItem extends EntityBase implements Serializable {

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
     * drug id
     */
    @Schema(description = "drug id")
    private Long drugId;

    /**
     * quantity
     */
    @Schema(description = "quantity")
    private Integer quantity;

    /**
     * Usage and dosage (e.g. 1 tablet, 2 times a day)
     */
    @Schema(description = "Usage and dosage (e.g. 1 tablet, 2 times a day)")
    private String dosage;


}
