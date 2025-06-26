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
 * 药房信息表
 * </p>
 *
 * @author lijin
 * @since 2025-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Include(name = "pharmacy")
@Schema(description = "药房信息表")
public class Pharmacy extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 药房名称
     */
    @Schema(description = "药房名称")
    private String name;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String location;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String contactPhone;

    /**
     * 营业时间
     */
    @Schema(description = "营业时间")
    private String businessHours;

}
