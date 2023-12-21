package com.laps.backend.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(User user) {
        super(user.getName(), user.getEmail(), user.getPassword(), "Admin");
        this.setId(user.getId());
    }

    public Admin(Employee employee) {
        super(employee.getName(), employee.getEmail(), employee.getPassword(), "Admin");
        this.setId(employee.getId());
    }

    public Admin(Manager manager) {
        super(manager.getName(), manager.getEmail(), manager.getPassword(), "Admin");
        this.setId(manager.getId());
    }
}
