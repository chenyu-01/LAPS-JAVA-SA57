package com.laps.backend.controllers;

import com.laps.backend.models.User;
import com.laps.backend.models.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.laps.backend.services.AdminServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 没有内容时返回 204 No Content

        }
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        Optional<User> user = adminService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = adminService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {

        User createdUser = adminService.createUser(newUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
