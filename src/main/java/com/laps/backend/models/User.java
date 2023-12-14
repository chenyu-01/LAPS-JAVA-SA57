package com.laps.backend.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // mandatory for name, role, username, password
    @Column(nullable = false)
    private String name;
    @Pattern(regexp = "Employee|Manager|Admin")
    @Column(nullable = false)
    private String role; // e.g., Employee, Manager, Admin, only 3 roles

    @Email
    @Column(nullable = false, unique = true)
    private String email; // unique email address
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

