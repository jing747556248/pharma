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
@Schema(description = "patient table")
public class Patient extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "primary key id")
    private Long id;

    /**
     * patient name
     */
    @Schema(description = "name")
    private String name;

    /**
     * patient age
     */
    @Schema(description = "age")
    private Integer age;

    /**
     * Gender (0: unknown, 1: male, 2: female)
     */
    @Schema(description = "Gender (0: unknown, 1: male, 2: female)")
    private Integer gender;

    /**
     * Contact phone Number
     */
    @Schema(description = "Contact phone Number")
    private String contactPhone;

}
