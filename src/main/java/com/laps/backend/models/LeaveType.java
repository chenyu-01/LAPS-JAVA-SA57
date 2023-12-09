package com.laps.backend.models;
import jakarta.persistence.*;
@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name; // e.g., Annual, Medical
    private int maxDays; // Max days allowed for this leave type

    // Standard getters and setters
}
