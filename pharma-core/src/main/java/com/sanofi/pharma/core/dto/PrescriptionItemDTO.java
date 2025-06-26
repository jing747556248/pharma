package com.sanofi.pharma.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "创建处方明细入参")
public class PrescriptionItemDTO implements Serializable {

    @Schema(description = "药品ID")
    private Long drugId;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "用法用量(如:每日2次,每次1片)")
    private String dosage;
}
