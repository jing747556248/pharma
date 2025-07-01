package com.sanofi.pharma.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrescriptionEventTypeEnum {
    // create prescription
    CREATE,

    // fulfill success
    FULFILL_SUCCESS,

    // fulfill fail
    FULFILL_FAIL
}
