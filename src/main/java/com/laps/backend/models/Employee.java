package com.laps.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Employee extends User{

    @OneToMany(mappedBy = "employee")
    private List<LeaveApplication> leaveApplications;
    public Employee() {
        super();
    }
}
