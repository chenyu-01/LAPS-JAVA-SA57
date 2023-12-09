package com.laps.backend.models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import  jakarta.persistence.*;
import java.util.Date;

@Entity
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private String type; // e.g., Annual, Medical, Compensation
    private String status; // e.g., Applied, Approved, Rejected, Deleted
    private String reason; // Reason for the leave

    // Standard getters and setters
}