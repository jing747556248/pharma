package com.sanofi.pharma.core.service;

import com.sanofi.pharma.core.dto.FulfillPrescriptionRequestDTO;
import com.sanofi.pharma.core.dto.PrescriptionDTO;
import com.sanofi.pharma.core.entity.Prescription;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 处方信息服务类
 *
 * @author lijin
 * @since 2025-06-19
 */
public interface PrescriptionService {

    Prescription createPrescription(PrescriptionDTO request);

    Boolean fulfillPrescription(FulfillPrescriptionRequestDTO request);
}
