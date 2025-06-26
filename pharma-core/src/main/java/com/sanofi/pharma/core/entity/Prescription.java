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
@Schema(description = "处方主表")
public class Prescription extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 所属药房ID
     */
    @Schema(description = "所属药房ID")
    private Long pharmacyId;

    /**
     * 患者id
     */
    @Schema(description = "患者id")
    private Long patientId;

    /**
     * 状态(0:未处理,1:配药成功,2:配药失败)
     */
    @Schema(description = "状态(0:未执行,1:配药成功,2:配药失败)")
    private Integer status;
}
