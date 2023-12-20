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
        super();
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setRole("Admin");
    }

    public Admin(Employee employee) {
        super();
        this.setName(employee.getName());
        this.setEmail(employee.getEmail());
        this.setPassword(employee.getPassword());
        this.setRole("Admin");
    }

    public Admin(Manager manager) {
        super();
        this.setName(manager.getName());
        this.setEmail(manager.getEmail());
        this.setPassword(manager.getPassword());
        this.setRole("Admin");
    }

}
