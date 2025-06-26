package com.sanofi.pharma.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrescriptionStatusEnum {

    NOT_FULFILL(0, "未执行"),

    FULFILL_SUCCESS(1, "配药成功"),

    FULFILL_FAIL(2, "配药失败")
    ;
    private final Integer code;

    private final String desc;
}
