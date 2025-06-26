package com.sanofi.pharma.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "执行处方入参")
public class FulfillPrescriptionRequestDTO implements Serializable {

    @Schema(description = "处方ID")
    @NotNull(message = "处方ID不能为空")
    private Long prescriptionId;
}
