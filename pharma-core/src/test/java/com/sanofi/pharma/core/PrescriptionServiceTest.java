package com.sanofi.pharma.core;

import com.sanofi.pharma.core.dto.FulfillPrescriptionRequestDTO;
import com.sanofi.pharma.core.dto.PrescriptionDTO;
import com.sanofi.pharma.core.dto.PrescriptionItemDTO;
import com.sanofi.pharma.core.entity.Prescription;
import com.sanofi.pharma.core.service.PrescriptionService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrescriptionServiceTest extends ElideTestBase {

    @Resource
    private PrescriptionService prescriptionService;

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
    public void fulfillPrescriptionTest() throws InterruptedException {
        final int THREAD_COUNT = 5;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        AtomicInteger counter = new AtomicInteger(0); // 计数器
        // 业务方法参数
        FulfillPrescriptionRequestDTO dto = new FulfillPrescriptionRequestDTO();
        dto.setPrescriptionId(5L);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    // 执行被测试方法
                    prescriptionService.fulfillPrescription(dto);
                    counter.incrementAndGet(); // 该处方执行的次数
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }
        startLatch.countDown();
        endLatch.await(); // 所有线程执行完成后 主线程继续执行
        assertEquals(THREAD_COUNT, counter.get()); // 验证结果
    }
}
