package com.laps.backend.models;
import jakarta.persistence.*;
@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // mandatory for name, maxDays. and name should be unique
    @Column(nullable = false, unique = true)
    private String name; // e.g., Annual, Medical, Compensation
    @Column(nullable = false)
    private int maxDays; // Max days allowed for this leave type

    // Standard getters and setters
}
