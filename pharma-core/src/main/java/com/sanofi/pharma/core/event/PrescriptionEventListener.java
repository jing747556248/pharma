package com.sanofi.pharma.core.event;

import com.alibaba.fastjson2.JSON;
import com.sanofi.pharma.core.entity.AuditLog;
import com.sanofi.pharma.core.entity.Prescription;
import com.sanofi.pharma.core.entity.PrescriptionItem;
import com.sanofi.pharma.core.enums.PrescriptionStatusEnum;
import com.sanofi.pharma.core.repository.AuditLogRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class PrescriptionEventListener implements ApplicationListener<PrescriptionEvent> {

    @Resource
    private AuditLogRepository auditLogRepository;

    @Override
    public void onApplicationEvent(PrescriptionEvent event) {
        log.info("监听到处方变化消息{}", JSON.toJSONString(event));
        Prescription prescription = event.getPrescription();
        List<PrescriptionItem> prescriptionItemList = event.getPrescriptionItemList();
        String failureReason = event.getFailureReason();

        String eventType = event.getEventType();
        switch (eventType) {
            case "CREATE":
                this.processCreateEvent(prescription, prescriptionItemList);
                break;
            case "FULFILL_SUCCESS":
                this.processFulfillSuccessEvent(prescription, prescriptionItemList);
                break;
            case "FULFILL_FAIL":
                this.processFulfillFailEvent(prescription, prescriptionItemList, failureReason);
                break;
        }
    }

    /**
     * 创建处方event
     */
    private void processCreateEvent(Prescription prescription, List<PrescriptionItem> prescriptionItemList) {
        AuditLog auditLog = new AuditLog();
        auditLog.setPrescriptionId(prescription.getId());
        auditLog.setPatientId(prescription.getPatientId());
        auditLog.setPharmacyId(prescription.getPharmacyId());
        auditLog.setRequiredDrugs(JSON.toJSONString(prescriptionItemList));
        auditLog.setDispensedStatus(PrescriptionStatusEnum.NOT_FULFILL.getCode()); // 处方创建-未执行
        auditLog.setCreateBy("admin");
        auditLog.setCreateTime(new Date());
        auditLog.setUpdateBy("admin");
        auditLog.setUpdateTime(new Date());
        auditLog.setIsDeleted(false);
        auditLogRepository.save(auditLog);
    }

    /**
     * 处方执行成功
     */
    private void processFulfillSuccessEvent(Prescription prescription, List<PrescriptionItem> prescriptionItemList) {
        AuditLog auditLog = new AuditLog();
        auditLog.setPrescriptionId(prescription.getId());
        auditLog.setPatientId(prescription.getPatientId());
        auditLog.setPharmacyId(prescription.getPharmacyId());
        auditLog.setRequiredDrugs(JSON.toJSONString(prescriptionItemList));
        auditLog.setDispensedStatus(PrescriptionStatusEnum.FULFILL_SUCCESS.getCode());
        auditLog.setCreateBy("admin");
        auditLog.setCreateTime(new Date());
        auditLog.setUpdateBy("admin");
        auditLog.setUpdateTime(new Date());
        auditLog.setIsDeleted(false);
        auditLogRepository.save(auditLog);
    }

    /**
     * 处方执行失败
     */
    private void processFulfillFailEvent(Prescription prescription, List<PrescriptionItem> prescriptionItemList, String failureReason) {
        AuditLog auditLog = new AuditLog();
        if (prescription != null) {
            auditLog.setPrescriptionId(prescription.getId());
            auditLog.setPatientId(prescription.getPatientId());
            auditLog.setPharmacyId(prescription.getPharmacyId());
        }
        if (CollectionUtils.isNotEmpty(prescriptionItemList)) {
            auditLog.setRequiredDrugs(JSON.toJSONString(prescriptionItemList));
        }
        auditLog.setDispensedStatus(PrescriptionStatusEnum.FULFILL_FAIL.getCode());
        auditLog.setFailureReason(failureReason);
        auditLog.setCreateBy("admin");
        auditLog.setCreateTime(new Date());
        auditLog.setUpdateBy("admin");
        auditLog.setUpdateTime(new Date());
        auditLog.setIsDeleted(false);
        auditLogRepository.save(auditLog);
    }
}
