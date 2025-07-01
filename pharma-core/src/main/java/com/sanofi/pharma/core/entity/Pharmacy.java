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
@Schema(description = "pharmacy table")
public class Pharmacy extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "primary key id")
    private Long id;

    /**
     * pharmacy name
     */
    @Schema(description = "name")
    private String name;

    /**
     * detail address
     */
    @Schema(description = "location")
    private String location;

    /**
     * Contact Number
     */
    @Schema(description = "Contact Number")
    private String contactPhone;

    /**
     * business hours
     */
    @Schema(description = "business hours")
    private String businessHours;

}
