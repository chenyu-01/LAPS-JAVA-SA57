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
    @Pattern(regexp = "User|Employee|Manager|Admin", message = "Role must be User, Employee, Manager, or Admin")
    private String role; // e.g., Employee, Manager, Admin, or User

    @Email
    @Column(nullable = false, unique = true)
    private String email; // unique email address
    @Size(min = 8, max = 20)
    @Column(nullable = false)
    private String password;

    // Relationships e.g., OneToMany for leave applications
    public  User() {
        super();
    }
    public User(String name, String email, String password, String role) {
        this.setEmail(email);
        this.setName(name);
        this.setPassword(password);
        this.setRole(role);
    }

    protected void setId(Long id) {
        this.id = id;
    }
}

