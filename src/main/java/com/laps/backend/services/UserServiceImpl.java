package com.laps.backend.services;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveType;
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

    private final UserRepository repository;

    private final LeaveTypeService leaveTypeService;

    private final ManagerRepository manager_repository;

    @Autowired
    public UserServiceImpl(UserRepository repository, LeaveTypeService leaveTypeService, ManagerRepository manager_repository) {
        this.repository = repository;
        this.leaveTypeService = leaveTypeService;
        this.manager_repository = manager_repository;
    }

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
