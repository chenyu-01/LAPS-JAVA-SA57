package com.laps.backend.services;

import com.laps.backend.models.Admin;
import com.laps.backend.models.Employee;
import com.laps.backend.models.Manager;
import com.laps.backend.models.User;
import com.laps.backend.repositories.EmployeeReposity;
import com.laps.backend.repositories.ManagerRepository;
import com.laps.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                if (updatedUser instanceof Employee) {
                    deleteUser(id);
                    yield employeeRepository.save((Employee) updatedUser);
                }
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
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User createUser(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        return userRepository.save(newUser);
    }
    @Override
    public User createAdmin(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Admin admin = new Admin(newUser);
        return userRepository.save(admin);
    }

    public Manager getManagerByName(String managerName) {
        return managerRepository.findByName(managerName);
    }
@Override
    public User createEmployee(User newUser, Manager manager) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Employee employee = new Employee(newUser);
        employee.setManager(manager);
        return employeeRepository.save(employee);
    }

    @Override
    public User createEmployee(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Employee employee = new Employee(newUser);
        return employeeRepository.save(employee);
    }

    @Override
    public User createManager(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Manager manager = new Manager(newUser);
        return managerRepository.save(manager);
    }

    public User getManagerByEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get().getManager();
        }else
            throw new IllegalArgumentException("Employee not found with id: " + id);

    }

    public User updateUser(User user) {
        if (user instanceof Employee)
            return employeeRepository.save((Employee) user);
        else return userRepository.save(user);
    }
}