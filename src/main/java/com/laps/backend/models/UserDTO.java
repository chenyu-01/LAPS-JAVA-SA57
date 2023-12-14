package com.laps.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter @Setter
public class UserDTO implements Serializable { // Data Transfer Object
    private String name;
    private String role;
    private String email;
    // No password field

    // Constructors
    public UserDTO(User other) {
        this.name = other.getName();
        this.role = other.getRole();
        this.email = other.getEmail();
    }


}
