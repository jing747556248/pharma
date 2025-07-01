package com.sanofi.pharma.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "fulfill prescription request param")
public class FulfillPrescriptionRequestDTO implements Serializable {

    @Schema(description = "prescriptionId")
    @NotNull(message = "prescription id cannot be null")
    private Long prescriptionId;
}
