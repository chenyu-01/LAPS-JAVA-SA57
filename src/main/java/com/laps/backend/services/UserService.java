package com.laps.backend.services;

import com.laps.backend.models.User;

public interface UserService {
    // Methods for handling users
    User findByEmail(String username);
    void save(User user);
}
