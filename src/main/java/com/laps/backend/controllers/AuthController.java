package com.laps.backend.controllers;

import com.laps.backend.models.User;
import com.laps.backend.services.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AuthController {
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

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication(HttpServletRequest request) {
        // Get the "auth_token" cookie from the request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    // Check if the cookie value is valid (e.g., matches a user session)
                    String username = cookie.getValue();
                    User user = userService.findByUsername(username);
                    if (user != null) {
                        // User is authenticated
                        return ResponseEntity.ok(user);
                    }
                }
            }
        }
        // If the cookie is not present or invalid, return an unauthorized response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
}