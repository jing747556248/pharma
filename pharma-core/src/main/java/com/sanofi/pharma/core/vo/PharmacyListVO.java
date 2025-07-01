package com.sanofi.pharma.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Schema(description = "pharmacy vo object")
@Data
public class PharmacyListVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "pharmacy name")
    private String name;

    @Schema(description = "location")
    private String location;

    @Schema(description = "contact phone")
    private String contactPhone;

    @Schema(description = "business hours")
    private String businessHours;


}
