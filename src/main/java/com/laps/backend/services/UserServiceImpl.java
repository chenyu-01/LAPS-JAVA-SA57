package com.laps.backend.services;

import com.laps.backend.models.User;
import com.laps.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
