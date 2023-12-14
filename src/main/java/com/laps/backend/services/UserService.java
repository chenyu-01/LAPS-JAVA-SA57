package com.laps.backend.services;

import com.laps.backend.models.User;
import com.laps.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {

    // Methods for handling users
    User findByUsername(String username);
    void save(User user);
}
