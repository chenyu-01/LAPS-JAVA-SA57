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
public class Manager extends Employee{

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates;

    public Manager(Employee employee) {
        super();
        this.setName(employee.getName());
        this.setEmail(employee.getEmail());
        this.setPassword(employee.getPassword());
        this.setRole(employee.getRole());
    }

    public Manager() {
        super();
    }
}
