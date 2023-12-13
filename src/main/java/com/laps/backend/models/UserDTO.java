package com.laps.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public class UserDTO implements Serializable { // Data Transfer Object
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String role;
    @Getter @Setter
    private String username;
    // No password field

    // Constructors
    public UserDTO(User other) {
        this.name = other.getName();
        this.role = other.getRole();
        this.username = other.getUsername();
    }


}
