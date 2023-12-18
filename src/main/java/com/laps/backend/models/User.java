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
    @Pattern(regexp = "Employee|Manager|Admin", message = "Role must be Employee, Manager, or Admin")
    @Column(nullable = false)
    private String role; // e.g., Employee, Manager, Admin, only 3 roles

    @Email
    @Column(nullable = false, unique = true)
    private String email; // unique email address
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number")
    @Column(nullable = false)
    private String password;

    // Relationships e.g., OneToMany for leave applications

}

