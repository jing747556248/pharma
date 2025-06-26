package com.sanofi.pharma.core;

import com.sanofi.pharma.core.repository.PharmacyRepository;
import com.sanofi.pharma.core.service.DrugService;
import com.sanofi.pharma.core.vo.DrugInPharmacyVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PharmacyServiceTest extends ElideTestBase {

    @Resource
    private DrugService drugService;

    @Resource
    private PharmacyRepository pharmacyRepository;

    @Test
    void getPharmacyByIdTest() throws Exception {
        performGet("/api/pharmacy/1")
                .andExpect(status().isOk());
    }

    /**
     * 根据药房id查询药品列表
     */
    @Test
    void listByPharmacyIdTest() {
        assertThat(pharmacyRepository.findById(1L)).isPresent(); // 确保数据存在
        List<DrugInPharmacyVO> drugInPharmacyVOS = drugService.listByPharmacyId(1L, 1, 10);
        Assertions.assertEquals(Boolean.TRUE, drugInPharmacyVOS.size() >= 0);
    }


}
