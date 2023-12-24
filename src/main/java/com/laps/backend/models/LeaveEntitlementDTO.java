package com.laps.backend.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Setter @Getter
public class LeaveEntitlementDTO implements Serializable {
    private int annualLeaveDays;
    private int medicalLeaveDays;
    private float compensationLeaveDays;

    public LeaveEntitlementDTO(UserLeaveEntitlement other) {
        this.annualLeaveDays = other.getAnnualEntitledDays();
        this.medicalLeaveDays = other.getMedicalEntitledDays();
        this.compensationLeaveDays = other.getCompensationEntitledDays();
    }
}
