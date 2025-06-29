package com.sanofi.pharma.core;

import com.sanofi.pharma.core.dto.PrescriptionDTO;
import com.sanofi.pharma.core.dto.PrescriptionItemDTO;
import com.sanofi.pharma.core.entity.Prescription;
import com.sanofi.pharma.core.repository.PrescriptionRepository;
import com.sanofi.pharma.core.service.PrescriptionService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrescriptionServiceTest extends ElideTestBase {

    @Resource
    private PrescriptionService prescriptionService;

    @Resource
    private PrescriptionRepository prescriptionRepository;

    /**
     * 创建处方
     */
    @Test
    void createPrescriptionTest() {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setPatientId(2L);
        dto.setPharmacyId(2L);
        List<PrescriptionItemDTO> list = new ArrayList<>();
        PrescriptionItemDTO itemDTO = new PrescriptionItemDTO();
        itemDTO.setDrugId(1L);
        itemDTO.setQuantity(6);
        itemDTO.setDosage("一日一次");
        list.add(itemDTO);

        PrescriptionItemDTO itemDTO1 = new PrescriptionItemDTO();
        itemDTO1.setDrugId(2L);
        itemDTO1.setQuantity(7);
        itemDTO1.setDosage("一日一次");
        list.add(itemDTO1);

        dto.setItemDTOList(list);
        Prescription prescription = prescriptionService.createPrescription(dto);
        assertEquals(0, prescription.getStatus());
    }

    /**
     * 执行处方
     */
    @Test
    public void fulfillPrescriptionTest() {



    }
}
