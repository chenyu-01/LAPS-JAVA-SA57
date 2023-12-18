package com.laps.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Manager extends Employee{

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates;
}
