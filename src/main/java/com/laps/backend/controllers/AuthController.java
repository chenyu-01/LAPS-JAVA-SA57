package com.laps.backend.controllers;

import com.laps.backend.models.User;
import com.laps.backend.models.UserDTO;
import com.laps.backend.services.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession sessionObj) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // ... handle login
        User user = userService.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        sessionObj.setAttribute("user", user);
        UserDTO userDTO = new UserDTO(user);
        // Create a cookie
        ResponseCookie authCookie = ResponseCookie.from("auth_token", username)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // expires in 7 days
                .build();

        // Return the response entity with the cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body(userDTO);
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication(HttpServletRequest request, HttpSession sessionObj) {
        // Get the "auth_token" cookie from the request
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    // Check if the cookie value is valid (e.g., matches a user session)
                    String username = cookie.getValue();
                    if (sessionObj.getAttribute("user") != null) {
                        User user = (User) sessionObj.getAttribute("user");
                        if (user.getUsername().equals(username)) {
                            // User is authenticated
                            return ResponseEntity.ok(new UserDTO(user));
                        }
                    }
                }
            }
        }
        // If the cookie is not present or invalid, return an unauthorized response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Create a cookie
        ResponseCookie authCookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0) // expires now
                .build();

        // Return the response entity with the cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body("Logged out");
    }
}