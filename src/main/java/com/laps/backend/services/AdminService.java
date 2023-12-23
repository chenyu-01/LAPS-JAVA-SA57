package com.laps.backend.services;

import com.laps.backend.models.Manager;
import com.laps.backend.models.User;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User editUser(User user);
    void deleteUser(Long id);

    User updateUser(Long id, User user);

    List<User> getUsersByRole(String role);

    User createUser(User newUser);

    User createAdmin(User newUser);

    User createEmployee(User newUser, Manager manager);

    User createEmployee(User newUser);

    User createManager(User newUser);

    List<User> searchUser(String[] keyword);
}
