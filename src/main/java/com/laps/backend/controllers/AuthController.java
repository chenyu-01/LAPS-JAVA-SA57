package com.laps.backend.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // ... handle login
        User user = userService.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        UserDTO userDTO = new UserDTO(user);
        session.setAttribute("user", userDTO);

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
    public ResponseEntity<?> checkAuthentication(HttpServletRequest request, HttpSession session) {
        // Get the "auth_token" cookie from the request
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        Cookie[] cookies = request.getCookies();
       if (cookies != null && userDTO != null) {
           for (Cookie cookie : cookies) {
               if (cookie.getName().equals("auth_token")) {
                   String username = cookie.getValue();
                   if (userDTO.getUsername().equals(username)) {
                       return ResponseEntity.ok(userDTO);
                   }
               }
           }
        }
        // If the cookie is not present or invalid, return an unauthorized response
        ResponseCookie authCookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0) // expires now
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body("Unauthorized");
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        ResponseCookie authCookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0) // expires now
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .body("Logged out");
    }
}