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
@Schema(description = "处方明细表")
public class PrescriptionItem extends EntityBase implements Serializable {

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
     * 药品ID
     */
    @Schema(description = "药品ID")
    private Long drugId;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer quantity;

    /**
     * 用法用量(如:每日2次,每次1片)
     */
    @Schema(description = "用法用量(如:每日2次,每次1片)")
    private String dosage;


}
