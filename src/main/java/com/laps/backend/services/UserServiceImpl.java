package com.laps.backend.services;

import com.laps.backend.models.Employee;
import com.laps.backend.models.Manager;
import com.laps.backend.models.User;
import com.laps.backend.repositories.ManagerRepository;
import com.laps.backend.repositories.UserRepository;
import com.laps.backend.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ManagerRepository manager_repository;

    @Override
    public User findByEmail(String username) {
        return repository.findByEmail(username);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public List<Employee> findAllEmployeeByManager(Manager manager) {
        return manager.getSubordinates();

    }


    @Override
    public Manager findManagerById(long id) {
        return manager_repository.findById(id);
    }
}
