package com.laps.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public LeaveApplicationDTO(LeaveApplication other) {
        this.id = other.getId().toString();
        this.startDate = df.format(other.getStartDate());
        this.endDate = df.format(other.getEndDate());
        this.type = other.getType();
        this.status = other.getStatus();
        this.comment = other.getComment();
        this.reason = other.getReason();
        this.employeeId = other.getEmployee().getId().toString();
        this.contactInfo = other.getContactInfo();
    }
}
