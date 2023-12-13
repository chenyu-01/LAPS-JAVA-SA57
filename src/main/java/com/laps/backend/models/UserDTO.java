package com.laps.backend.models;

public class UserDTO { // Data Transfer Object
    private Long id;
    private String name;
    private String role;
    private String username;
    // No password field

    // Constructors
    public UserDTO(User other) {
        this.id = other.getId();
        this.name = other.getName();
        this.role = other.getRole();
        this.username = other.getUsername();
    }
}
