package com.laps.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Employee extends User{


    @ManyToOne
    @JoinColumn(name = "manager_id")
    //@JsonManagedReference
    private Manager manager; // explicitly set manager_id foreign key column in employee table
    @OneToMany(mappedBy = "employee",cascade = CascadeType.REMOVE)
    private List<LeaveApplication> leaveApplications;
    public Employee() {
        super();
    }

    // constructor for converting User to Employee
    public Employee(User user) {
        super(user.getName(), user.getEmail(), user.getPassword(), "Employee");
        this.setId(user.getId());
    }

    // constructor for converting Manager to Employee
    public Employee(Manager manager) {
        super(manager.getName(), manager.getEmail(), manager.getPassword(), "Employee");
        this.setId(manager.getId());
    }

    public Employee(Admin admin) {
        super(admin.getName(), admin.getEmail(), admin.getPassword(), "Employee");
        this.setId(admin.getId());
    }
}
