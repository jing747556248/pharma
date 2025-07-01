package com.sanofi.pharma.core.entity;

import com.sanofi.pharma.core.entity.check.PharmacyDrugRelationshipCreateCheck;
import com.yahoo.elide.annotation.CreatePermission;
import com.yahoo.elide.annotation.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 药房-药品关联表
 * </p>
 *
 * @author lijin
 * @since 2025-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Include(name = "pharmacyDrugRelationship")
@CreatePermission(expression = PharmacyDrugRelationshipCreateCheck.CREATE_CHECK)
@Schema(description = "pharmacy drug relationship table")
public class PharmacyDrugRelationship extends EntityBase implements Serializable {

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
     * drug id
     */
    @Schema(description = "drug id")
    private Long drugId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drugId", insertable = false, updatable = false)
    private Drug drug;

    /**
     * stock
     */
    @Schema(description = "stock")
    private Integer stock;

    @Version
    private Integer version;

}
