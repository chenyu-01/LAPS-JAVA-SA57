package com.laps.backend.controllers;

import com.laps.backend.models.User;
import com.laps.backend.models.UserDTO;
import com.laps.backend.services.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserServiceImpl  userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // ... handle login
        User user = userService.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        UserDTO userDTO = new UserDTO(user);
        session.setAttribute("user", userDTO);


        // Return the response entity with the cookie
        return ResponseEntity.ok()
                .body(userDTO);
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized");
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok()
                .body("Logged out");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // ... handle registration
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        userService.save(user);
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }
}