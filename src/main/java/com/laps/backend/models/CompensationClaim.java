package com.laps.backend.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Entity
@Getter @Setter
public class CompensationClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateOfOvertime;
    private Integer hoursWorked;
    private String description;
    private Long employeeId;
    private String status; // e.g., Applied, Approved, Rejected

}