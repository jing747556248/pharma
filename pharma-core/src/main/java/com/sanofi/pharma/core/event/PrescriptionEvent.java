package com.sanofi.pharma.core.event;

import com.sanofi.pharma.core.entity.Prescription;
import com.sanofi.pharma.core.entity.PrescriptionItem;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 处方操作事件对象
 *
 * @author lijin
 * @since 2025-06-23
 */
@Getter
public class PrescriptionEvent extends ApplicationEvent {

    private final Prescription prescription;

    private final List<PrescriptionItem> prescriptionItemList;

    // 失败原因
    private final String failureReason;

    private final String eventType;

    public PrescriptionEvent(Object source, Prescription prescription, List<PrescriptionItem> prescriptionItemList, String failureReason, String eventType) {
        super(source);
        this.prescription = prescription;
        this.prescriptionItemList = prescriptionItemList;
        this.failureReason = failureReason;
        this.eventType = eventType;
    }
}
