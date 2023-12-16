package com.laps.backend.services;

import org.springframework.stereotype.Service;

import com.laps.backend.models.User;
@Service
public interface UserService {
    // Methods for handling users
    User findByEmail(String username);
    void save(User user);
}
