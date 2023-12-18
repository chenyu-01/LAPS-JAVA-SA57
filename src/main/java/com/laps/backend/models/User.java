package com.laps.backend.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.Getter;
@Setter @Getter
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Getter(AccessLevel.NONE) // disable getter for id
    @Setter(AccessLevel.NONE) // disable setter for id
    private Long id;
    // mandatory for name, role, username, password
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Role role; // e.g., Employee, Manager, Admin, only 3 roles

    @Email
    @Column(nullable = false, unique = true)
    private String email; // unique email address
    @Size(min = 8, max = 20)
    @Column(nullable = false)
    private String password;

    // Relationships e.g., OneToMany for leave applications

}

