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
    public User findByEmail(String username) {
        return repository.findByEmail(username);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }
}
