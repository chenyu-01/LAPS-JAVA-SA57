package com.laps.backend.services;

import com.laps.backend.models.User;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User editUser(User user);
    void deleteUser(Long id);

    User updateUser(Long id, User user);

    User createUser(User newUser);
}
