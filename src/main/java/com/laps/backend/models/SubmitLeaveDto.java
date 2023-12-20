package com.laps.backend.models;

import java.time.LocalDate;

public class SubmitLeaveDto {

    private LocalDate fromDate;
    private LocalDate toDate;
    private String leaveCategory;
    private String additionalReasons;
    private String workDissemination;
    private String contactDetails;

    // Constructors, getters, and setters

    public SubmitLeaveDto() {
        // Default constructor
    }

    public SubmitLeaveDto(LocalDate fromDate, LocalDate toDate, String leaveCategory,
                          String additionalReasons, String workDissemination, String contactDetails) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.leaveCategory = leaveCategory;
        this.additionalReasons = additionalReasons;
        this.workDissemination = workDissemination;
        this.contactDetails = contactDetails;
    }

    // Getters and setters for all fields

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getLeaveCategory() {
        return leaveCategory;
    }

    public void setLeaveCategory(String leaveCategory) {
        this.leaveCategory = leaveCategory;
    }

    public String getAdditionalReasons() {
        return additionalReasons;
    }

    public void setAdditionalReasons(String additionalReasons) {
        this.additionalReasons = additionalReasons;
    }

    public String getWorkDissemination() {
        return workDissemination;
    }

    public void setWorkDissemination(String workDissemination) {
        this.workDissemination = workDissemination;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }
}
