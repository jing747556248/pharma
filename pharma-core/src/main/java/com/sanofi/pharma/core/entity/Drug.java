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
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

/**
 * <p>
 * 药物信息表
 * </p>
 *
 * @author lijin
 * @since 2025-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Include(name = "drug")
@Schema(description = "drug table")
public class Drug extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "primary key id")
    private Long id;

    /**
     * drug name
     */
    @Schema(description = "name")
    private String name;

    /**
     * manufacturer
     */
    @Schema(description = "manufacturer")
    private String manufacturer;

    @Schema(description = "batch number")
    private String batchNumber;

    @Schema(description = "production date")
    private String productionDate;

    /**
     * expiry date
     */
    @Schema(description = "expiry date")
    private String expiryDate;

    /**
     * stock
     */
    @Schema(description = "stock")
    private Integer stock;

    /**
     * price
     */
    @Schema(description = "price")
    private BigDecimal price;

}
