package com.laps.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Manager extends Employee{

    @OneToMany(mappedBy = "manager")
    //@JsonBackReference
    private List<Employee> subordinates;

    public Manager(Employee employee) {
        super();
        this.setId(employee.getId());
        this.setName(employee.getName());
        this.setEmail(employee.getEmail());
        this.setPassword(employee.getPassword());
        this.setRole(employee.getRole());
    }

    public Manager(User user) {
        super();
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setRole("Manager");
    }

    public Manager(Admin admin) {
        super();
        this.setId(admin.getId());
        this.setName(admin.getName());
        this.setEmail(admin.getEmail());
        this.setPassword(admin.getPassword());
        this.setRole("Manager");
    }
    public Manager() {
        super();
    }
}
