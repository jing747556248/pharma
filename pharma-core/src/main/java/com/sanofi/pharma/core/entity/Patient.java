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
 * 患者信息表
 * </p>
 *
 * @author lijin
 * @since 2025-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Include(name = "patient")
@Schema(description = "患者信息表")
public class Patient extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 患者姓名
     */
    @Schema(description = "患者姓名")
    private String name;

    /**
     * 患者年龄
     */
    @Schema(description = "患者年龄")
    private Integer age;

    /**
     * 性别(0:未知,1:男,2:女)
     */
    @Schema(description = "性别(0:未知,1:男,2:女)")
    private Integer gender;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String contactPhone;

}
