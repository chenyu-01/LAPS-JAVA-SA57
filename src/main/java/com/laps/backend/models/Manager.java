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

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true)
    private Manager manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates;
}
