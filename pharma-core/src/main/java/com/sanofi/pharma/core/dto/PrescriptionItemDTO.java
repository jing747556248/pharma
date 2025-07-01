package com.sanofi.pharma.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "create prescription item request param")
public class PrescriptionItemDTO implements Serializable {

    @Schema(description = "drug id")
    private Long drugId;

    @Schema(description = "quantity")
    private Integer quantity;

    @Schema(description = "Usage and dosage (e.g. 1 tablet, 2 times a day)")
    private String dosage;
}
