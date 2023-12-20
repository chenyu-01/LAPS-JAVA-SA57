package com.laps.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Employee extends User{

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager; // explicitly set manager_id foreign key column in employee table
    @OneToMany(mappedBy = "employee")
    private List<LeaveApplication> leaveApplications;
    public Employee() {
        super();
    }
}
