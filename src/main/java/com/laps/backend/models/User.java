package com.laps.backend.models;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // mandatory for name, role, username, password
//    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
    private String role; // e.g., Employee, Manager, Admin
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    // Relationships e.g., OneToMany for leave applications
    // Standard getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}