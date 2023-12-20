package com.laps.backend.repositories;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveType;
import com.laps.backend.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    //Find manager by id
    Manager findById(long id);

    //List all related employees
    List<Employee> findAllEmplyeeByManager(Manager manager);
}
