package com.sanofi.pharma.core.controller;

import com.sanofi.pharma.common.dto.RespBody;
import com.sanofi.pharma.core.dto.FulfillPrescriptionRequestDTO;
import com.sanofi.pharma.core.dto.PrescriptionDTO;
import com.sanofi.pharma.core.entity.Prescription;
import com.sanofi.pharma.core.service.PrescriptionService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * @author lijin
 * @since 2025-06-19
 */
@Schema(description = "prescription controller")
@RequestMapping("/api/prescription")
@RestController
public class PrescriptionController {

    @Resource
    private PrescriptionService prescriptionService;

    @PostMapping(value = "/create")
    @Schema(description = "create prescription")
    public RespBody<Prescription> createPrescription(@RequestBody PrescriptionDTO request) {
        return RespBody.ok(prescriptionService.createPrescription(request));
    }

    @PostMapping(value = "/fulfill")
    @Schema(description = "fulfill prescription")
    public RespBody<Boolean> fulfillPrescription(@RequestBody FulfillPrescriptionRequestDTO request) {
        return RespBody.ok(prescriptionService.fulfillPrescription(request));
    }


}
