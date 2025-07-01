package com.sanofi.pharma.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "create prescription request param")
public class PrescriptionDTO implements Serializable {

    @Schema(description = "pharmacy id")
    @NotNull(message = "pharmacy id cannot be null")
    private Long pharmacyId;

    @Schema(description = "patient id")
    @NotNull(message = "patient id cannot be null")
    private Long patientId;

    @Schema(description = "drug item list")
    private List<PrescriptionItemDTO> itemDTOList;
}
