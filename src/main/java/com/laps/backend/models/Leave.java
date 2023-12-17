package com.laps.backend.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class LeaveType {
    @Id
    @Setter(AccessLevel.NONE) // disable setter for id
    @Getter(AccessLevel.NONE) // disable getter for id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // mandatory for name, maxDays. and name should be unique
    @Column(nullable = false, unique = true)
    private String name; // e.g., Annual, Medical, Compensation, etc.
    @Column(nullable = false)
    private int maxDays; // Max days allowed for this leave type

    // Standard getters and setters
}
