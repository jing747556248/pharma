package com.sanofi.pharma.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrescriptionEventTypeEnum {
    // 创建处方
    CREATE,

    // 处方执行成功
    FULFILL_SUCCESS,

    // 处方执行失败
    FULFILL_FAIL
}
