package com.sanofi.pharma.core.controller;

import com.sanofi.pharma.common.dto.RespBody;
import com.sanofi.pharma.core.service.DrugService;
import com.sanofi.pharma.core.service.PharmacyService;
import com.sanofi.pharma.core.vo.DrugInPharmacyVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Schema(description = "药房管理")
@RequestMapping("/api/pharmacy")
@RestController
public class PharmacyController {

    @Resource
    private PharmacyService pharmacyService;

    @Schema(description = "根据药房id查询药品列表")
    @GetMapping("/{pharmacyId}/drugs")
    public RespBody<List<DrugInPharmacyVO>> getDrugsByPharmacyId(@PathVariable Long pharmacyId,
                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return RespBody.ok(pharmacyService.getDrugsByPharmacyId(pharmacyId, pageNum, pageSize));
    }
}
