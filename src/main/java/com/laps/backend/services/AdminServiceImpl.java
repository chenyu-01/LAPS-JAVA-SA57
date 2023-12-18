package com.laps.backend.services;

import com.laps.backend.models.User;
import com.laps.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository; // 假设这是您的 JPA 存储库

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
       // if (user.getId() != null && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
       // }
       // throw new IllegalArgumentException("User not found with id: " + user.getId());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
        public User updateUser(Long id, User updatedUser) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
            user.setName(updatedUser.getName());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user); // 这应该是更新操作，而不是插入
    }

    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }
}