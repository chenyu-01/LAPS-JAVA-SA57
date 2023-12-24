package com.laps.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "leaveEntitlement")
public class UserLeaveEntitlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private int annualEntitledDays;
    private int medicalEntitledDays;
    private float compensationEntitledDays; // half day increment


    // Constructor, getters and setters
}
