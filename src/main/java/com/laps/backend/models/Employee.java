package com.laps.backend.models;

import com.laps.backend.controllers.AdminController;
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

    // constructor for converting User to Employee
    public Employee(User user) {
        super();
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setRole("Employee");
    }

    // constructor for converting Manager to Employee
    public Employee(Manager manager) {
        super();
        this.setName(manager.getName());
        this.setEmail(manager.getEmail());
        this.setPassword(manager.getPassword());
        this.setRole("Employee");
    }

    public Employee(Admin admin) {
        super();
        this.setName(admin.getName());
        this.setEmail(admin.getEmail());
        this.setPassword(admin.getPassword());
        this.setRole("Employee");
    }
}
