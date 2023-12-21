package com.laps.backend.models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import  jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@Entity
public class LeaveApplication {
    @Id
    @Setter(AccessLevel.NONE) // disable setter for id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private String type; // e.g., Annual, Medical, Compensation, etc.
    @Column(nullable = false)
    @Pattern(regexp = "Applied|Approved|Rejected|Deleted|Cancelled|Updated", message = "Status must be Applied, Approved, Rejected, Deleted, Cancelled, or Updated")
    private String status; // e.g., Applied, Approved, Rejected, Deleted, Cancelled，Updated
    private String comment; // comment for the leave application
    @Column(nullable = false)
    private String reason; // Reason for the leave
    private String contactInfo; // Contact information if overseas leave
    @Column(nullable = false)
    private boolean isOverseas; // Whether the leave is overseas

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // Employee who applied for the leave
    // Standard getters and setters
}