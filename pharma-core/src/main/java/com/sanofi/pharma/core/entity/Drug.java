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
@Schema(description = "药物信息表")
public class Drug extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 药物名称
     */
    @Schema(description = "药物名称")
    private String name;

    /**
     * 制造商
     */
    @Schema(description = "制造商")
    private String manufacturer;

    /**
     * 批号
     */
    @Schema(description = "批号")
    private String batchNumber;

    /**
     * 生产日期
     */
    @Schema(description = "生产日期")
    private String productionDate;

    /**
     * 有效期至
     */
    @Schema(description = "有效期至")
    private String expiryDate;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private Integer stock;

    /**
     * 单价(元)
     */
    @Schema(description = "单价(元)")
    private BigDecimal price;

}
