package com.laps.backend.models;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String role; // e.g., Employee, Manager, Admin
    private String username;
    private String password;

    // Relationships e.g., OneToMany for leave applications
    // Standard getters and setters
}