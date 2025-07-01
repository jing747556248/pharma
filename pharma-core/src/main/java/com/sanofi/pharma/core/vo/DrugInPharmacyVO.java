package com.sanofi.pharma.core.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Schema(description = "Pharmacy's contracted drugs")
@Data
public class DrugInPharmacyVO implements Serializable {

    @Schema(description = "drug id")
    private Long drugId;

    @Schema(description = "stock")
    private Integer stock;

    @Schema(description = "drug name")
    private String name;

    @Schema(description = "manufacturer")
    private String manufacturer;

    @Schema(description = "batch number")
    private String batchNumber;

    @Schema(description = "production date")
    private String productionDate;

    @Schema(description = "expiry date")
    private String expiryDate;

}
