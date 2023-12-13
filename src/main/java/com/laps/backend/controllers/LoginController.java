package com.laps.backend.controllers;

import com.laps.backend.models.User;
import com.laps.backend.services.UserServiceImpl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // ... handle login
        User user = userService.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        // Create a cookie
        ResponseCookie authCookie = ResponseCookie.from("auth_token", username)
                .httpOnly(true)
                .path("/")
                .build();

        // Return the response entity with the cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body(user);
    }
}