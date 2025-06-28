package com.sanofi.pharma.core.service;


import com.sanofi.pharma.core.vo.DrugInPharmacyVO;

import java.util.List;

/**
 * 药房信息服务类
 *
 * @author lijin
 * @since 2025-06-16
 */
public interface PharmacyService {

    List<DrugInPharmacyVO> getDrugsByPharmacyId(Long pharmacyId, Integer pageNum, Integer pageSize);

}
