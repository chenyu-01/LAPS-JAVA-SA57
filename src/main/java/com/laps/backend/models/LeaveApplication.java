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
    @Getter(AccessLevel.NONE) // disable getter for id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private String type; // e.g., Annual, Medical, Compensation, etc.
    @Column(nullable = false)
    @Pattern(regexp = "Applied|Approved|Rejected|Deleted", message = "Status must be Applied, Approved, Rejected, or Deleted")
    private String status; // e.g., Applied, Approved, Rejected, Deleted
    private String reason; // Reason for the leave

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // Employee who applied for the leave
    // Standard getters and setters
}