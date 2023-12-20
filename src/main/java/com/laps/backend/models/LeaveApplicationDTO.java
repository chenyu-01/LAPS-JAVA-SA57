package com.laps.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LeaveApplicationDTO implements Serializable {
    private String id;
    private String startDate;
    private String endDate;
    private String type;
    private String status;
    private String comment;
    private String reason;
    private String contactInfo;
    private String employeeId;

    public LeaveApplicationDTO(LeaveApplication other) {
        this.id = other.getId().toString();
        this.startDate = other.getStartDate().toString();
        this.endDate = other.getEndDate().toString();
        this.type = other.getType();
        this.status = other.getStatus();
        this.comment = other.getComment();
        this.reason = other.getReason();
        this.employeeId = other.getEmployee().getId().toString();
        this.contactInfo = other.getContactInfo();
    }
}
