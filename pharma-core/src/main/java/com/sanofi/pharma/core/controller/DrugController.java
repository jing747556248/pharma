package com.sanofi.pharma.core.controller;

import com.sanofi.pharma.common.dto.RespBody;
import com.sanofi.pharma.core.service.DrugService;
import com.sanofi.pharma.core.vo.DrugInPharmacyVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijin
 * @since 2025-06-16
 */
@Schema(description = "药品管理")
@RequestMapping("/api/drug")
@RestController
public class DrugController {

    @Resource
    private DrugService drugService;

    @Schema(description = "根据药房id查询药品列表")
    @GetMapping("/pharmacy/{pharmacyId}")
    public RespBody<List<DrugInPharmacyVO>> listByPharmacyId(@PathVariable Long pharmacyId,
                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return RespBody.ok(drugService.listByPharmacyId(pharmacyId, pageNum, pageSize));
    }
}
