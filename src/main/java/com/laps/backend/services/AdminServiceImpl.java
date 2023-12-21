package com.laps.backend.services;

import com.laps.backend.models.Admin;
import com.laps.backend.models.Employee;
import com.laps.backend.models.Manager;
import com.laps.backend.models.User;
import com.laps.backend.repositories.EmployeeReposity;
import com.laps.backend.repositories.ManagerRepository;
import com.laps.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeReposity employeeRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User editUser(User user) {
       if (user.getId() != null && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
       throw new IllegalArgumentException("User not found with id: " + user.getId());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        //user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        //same user?
        if (!Objects.equals(updatedUser.getId(), id)) {
            throw new IllegalArgumentException("User id mismatch");
        }

        String newRole = updatedUser.getRole();
        updatedUser.setPassword(user.getPassword());

        return switch (newRole) {
            case "Manager" -> {
                Manager manager = new Manager(updatedUser);
                deleteUser(id);
                yield managerRepository.save(manager);
            }
            case "Employee" -> {
                Employee employee = new Employee(updatedUser);
                deleteUser(id);
                yield employeeRepository.save(employee);
            }
            case "Admin" -> {
                Admin admin = new Admin(updatedUser);
                deleteUser(id);
                yield userRepository.save(admin);
            }
            default -> throw new IllegalStateException("Unexpected value: " + newRole);
        };
    }

    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }
}