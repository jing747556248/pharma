package com.sanofi.pharma.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "创建处方入参")
public class PrescriptionDTO implements Serializable {

    @Schema(description = "所属药房ID")
    @NotNull(message = "药房ID不能为空")
    private Long pharmacyId;

    @Schema(description = "患者id")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "药品明细")
    private List<PrescriptionItemDTO> itemDTOList;
}
