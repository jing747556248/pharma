package com.sanofi.pharma.core.entity;

import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.graphql.annotation.GraphQLDescription;
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
@Schema(description = "药房-药品关联表")
public class PharmacyDrugRelationship extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 药房ID
     */
    @Schema(description = "药房ID")
    private Long pharmacyId;

    /**
     * 药品ID
     */
    @Schema(description = "药品ID")
    private Long drugId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drugId", insertable = false, updatable = false)
    private Drug drug;

    /**
     * 该药房库存量
     */
    @Schema(description = "该药房库存量")
    private Integer stock;

    @Version
    private Integer version;

}
