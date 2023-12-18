package com.laps.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Employee extends User{

    @ManyToOne(optional = true)
    private Manager manager; // implicitly set manager_id foreign key column in employee table
    @OneToMany(mappedBy = "employee")
    private List<LeaveApplication> leaveApplications;
    public Employee() {
        super();
    }
}
