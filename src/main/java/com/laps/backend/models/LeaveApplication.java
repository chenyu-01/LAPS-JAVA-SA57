package com.laps.backend.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class LeaveApplication {
    @Id
    @Setter(AccessLevel.NONE) // disable setter for id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
     // Link to LeaveType.name
    private String type; // e.g., Annual, Medical, Compensation, etc.
    @Column(nullable = false)
    @Pattern(regexp = "Applied|Approved|Rejected|Deleted|Cancelled|Updated", message = "Status must be Applied, Approved, Rejected, Deleted, Cancelled, or Updated")
    private String status; // e.g., Applied, Approved, Rejected, Deleted, Cancelled，Updated
    private String comment; // comment for the leave application
    @NotBlank(message = "Reason cannot be blank")
    private String reason; // Reason for the leave
    private String contactInfo; // Contact information if overseas leave
    @Column(nullable = false)
    private Boolean isOverseas; // Whether the leave is overseas

    private String workDissemination; // Work dissemination before the leave
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // Employee who applied for the leave


    public LeaveApplication() {

    }

}

