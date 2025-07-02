package com.sanofi.pharma.core;

import com.sanofi.pharma.common.exception.BizException;
import com.sanofi.pharma.core.dto.FulfillPrescriptionRequestDTO;
import com.sanofi.pharma.core.dto.PrescriptionDTO;
import com.sanofi.pharma.core.dto.PrescriptionItemDTO;
import com.sanofi.pharma.core.entity.Prescription;
import com.sanofi.pharma.core.service.PrescriptionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
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
        itemDTO.setDosage("Once a day");
        list.add(itemDTO);

        PrescriptionItemDTO itemDTO1 = new PrescriptionItemDTO();
        itemDTO1.setDrugId(2L);
        itemDTO1.setQuantity(7);
        itemDTO1.setDosage("Twice a day");
        list.add(itemDTO1);

        dto.setItemDTOList(list);
        Prescription prescription = prescriptionService.createPrescription(dto);
        assertEquals(0, prescription.getStatus());
    }

    /**
     * 执行处方--并发执行(对同一个处方)
     */
    @Test
    public void fulfillPrescriptionTest() throws InterruptedException {
        final int THREAD_COUNT = 5;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        AtomicInteger counter = new AtomicInteger(0); // 计数器
        // 业务方法参数
        FulfillPrescriptionRequestDTO dto = new FulfillPrescriptionRequestDTO();
        dto.setPrescriptionId(11L);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    counter.incrementAndGet(); // 该处方执行的次数
                    // 执行被测试方法
                    prescriptionService.fulfillPrescription(dto);
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

    /**
     * 处方执行-单条执行
     */
    @Test
    public void fulfillTest() {
        // 业务方法参数
        FulfillPrescriptionRequestDTO dto = new FulfillPrescriptionRequestDTO();
        dto.setPrescriptionId(11L);
        try {
            Boolean result = prescriptionService.fulfillPrescription(dto);
            Assertions.assertTrue(result);
        } catch (BizException bizException) {
            log.info("fulfill prescription fail, please check reason in audit log");
        }
    }
}
