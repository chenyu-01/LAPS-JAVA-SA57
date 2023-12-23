package com.laps.backend.services;

import com.laps.backend.models.Employee;
import com.laps.backend.models.Manager;
import com.laps.backend.models.User;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    // Methods for handling users
    User findByEmail(String username);
    void save(User user);

    //list all employees belong to a manager
    List<Employee> findAllEmployeeByManager(Manager manager);

    //find manager by id
    Manager findManagerById(long id);

}
