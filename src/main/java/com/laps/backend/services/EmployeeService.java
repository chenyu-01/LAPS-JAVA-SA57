package com.laps.backend.services;

import com.laps.backend.models.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findById(Long id);
}
