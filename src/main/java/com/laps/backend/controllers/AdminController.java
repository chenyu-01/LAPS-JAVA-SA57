package com.laps.backend.controllers;

import com.laps.backend.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.laps.backend.services.AdminServiceImpl;

import java.util.*;

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
            return ResponseEntity.noContent().build(); // 204 No Content

        }
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDTO>> getUserById(@PathVariable Long id) {
        Optional<User> user = adminService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        UserDTO userDTO = new UserDTO(user.get());
        return ResponseEntity.ok(Optional.of(userDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> userData) {
        User user = adminService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        //check if user match
        if (!Objects.equals(user.getEmail(), (String) userData.get("email"))) {
            throw new IllegalArgumentException("User email mismatch");
        }

        //if role do not change
        if (user.getRole().equals((String) userData.get("role"))) {
            switch (user.getRole()) {
                case "Manager" :
                case "Employee" :{
                    if (user instanceof Employee) {
                        Manager manager = adminService.getManagerByName((String) userData.get("authName"));
                        if (manager == null)
                            throw new IllegalArgumentException("Manager not found with name: " + userData.get("authName"));
                        ((Employee) user).setManager(manager);
                    }
                    adminService.updateUser(user);
                    return ResponseEntity.ok().build();
                }
                case "Admin" :
                    return ResponseEntity.ok(user);
                default:
                    throw new IllegalArgumentException("Invalid role");
            }
        }

        user.setRole((String) userData.get("role"));

        //if user is not admin, try to set manager
        if (!user.getRole().equals("Admin")) {
            String manager_name = (String) userData.get("authName");
            Manager manager = adminService.getManagerByName(manager_name);

            if (manager == null)
                throw new IllegalArgumentException("Manager not found with name: " + manager_name);
            //
            if (user.getRole().equals("Employee")){
                Employee employee = new  Employee(user);
                employee.setManager(manager);
                user = employee;
            }
            else {
                Manager manager1 = new Manager(user);
                manager1.setManager(manager);
                user = manager1;
            }
        }
        User updatedUser = adminService.updateUser(id, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/Manager/{id}")
    public ResponseEntity<UserDTO> GetManagerByEmployee(@PathVariable Long id) {
        //check whether id matchs a manager or employee
        User user = adminService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        if (!user.getRole().equals("Employee") && !user.getRole().equals("Manager")) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        //try to find manager
        User manager = adminService.getManagerByEmployee(id);
        if (manager == null) {
            return ResponseEntity.noContent().build(); // 204 No Content

        }
        return ResponseEntity.ok(new UserDTO(manager));
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody Map<String, Object> userData) {
        User newUser = new User();
        newUser.setName((String) userData.get("name"));
        newUser.setEmail((String) userData.get("email"));
        newUser.setPassword((String) userData.get("password"));
        newUser.setRole((String) userData.get("role"));


        switch (newUser.getRole()) {
            case "Manager" -> {
                String manager_name = (String) userData.get("authName");
                Manager manager = adminService.getManagerByName(manager_name);

                if (manager == null) {
                    newUser = adminService.createManager(newUser);
                }else {
                    newUser = adminService.createManager(newUser, manager);
                }
            }
            case "Employee" -> {
                String manager_name = (String) userData.get("authName");
                Manager manager = adminService.getManagerByName(manager_name);

                if (manager == null) {
                    newUser = adminService.createEmployee(newUser);
                }else {
                    newUser = adminService.createEmployee(newUser, manager);
                }
            }
            case "Admin" -> {
                newUser = adminService.createAdmin(newUser);
            }
            case "User" -> {
                newUser = adminService.createUser(newUser);
            }
            default -> throw new IllegalArgumentException("Invalid role");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/users/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String role) {
        List<User> users = adminService.getUsersByRole(role);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content

        }
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }

    @PostMapping("/user/Query")
    public ResponseEntity<List<UserDTO>> getUsersByQuery(@RequestBody Map<String, String> Query) {
        String[] Keywords = Query.get("query").split(" ");
        List<User> users = adminService.searchUser(Keywords);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content

        }
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user));
        }
        return ResponseEntity.ok(userDTOS);
    }
}
