package com.sanofi.pharma.core.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Schema(description = "药房的合同药")
@Data
public class DrugInPharmacyVO implements Serializable {

    @Schema(description = "药品ID")
    private Long drugId;

    @Schema(description = "该药房库存量")
    private Integer stock;

    @Schema(description = "药物名称")
    private String name;

    @Schema(description = "制造商")
    private String manufacturer;

    @Schema(description = "批号")
    private String batchNumber;

    @Schema(description = "生产日期")
    private String productionDate;

    @Schema(description = "有效期至")
    private String expiryDate;

}
