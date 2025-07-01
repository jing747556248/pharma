package com.sanofi.pharma.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrescriptionStatusEnum {

    NOT_FULFILL(0, "not fulfill"),

    FULFILL_SUCCESS(1, "dispensed success"),

    FULFILL_FAIL(2, "dispensed fail")
    ;
    private final Integer code;

    private final String desc;
}
