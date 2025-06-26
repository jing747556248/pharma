package com.sanofi.pharma.core.service;


import com.sanofi.pharma.core.vo.DrugInPharmacyVO;

import java.util.List;

/**
 * 药品信息服务类
 *
 * @author lijin
 * @since 2025-06-16
 */
public interface DrugService {

    List<DrugInPharmacyVO> listByPharmacyId(Long pharmacyId, Integer pageNum, Integer pageSize);
}
