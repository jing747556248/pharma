package com.sanofi.pharma.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Schema(description = "药房视图对象")
@Data
public class PharmacyListVO implements Serializable {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "药房名称")
    private String name;

    @Schema(description = "详细地址")
    private String location;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "营业时间")
    private String businessHours;


}
