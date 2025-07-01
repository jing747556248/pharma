package com.sanofi.pharma.core.controller;

import com.sanofi.pharma.common.dto.RespBody;
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
@Schema(description = "pharmacy controller")
@RequestMapping("/api/pharmacy")
@RestController
public class PharmacyController {

    @Resource
    private PharmacyService pharmacyService;

    @Schema(description = "query drugs list by pharmacy id")
    @GetMapping("/{pharmacyId}/drugs")
    public RespBody<List<DrugInPharmacyVO>> getDrugsByPharmacyId(@PathVariable Long pharmacyId,
                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return RespBody.ok(pharmacyService.getDrugsByPharmacyId(pharmacyId, pageNum, pageSize));
    }
}
