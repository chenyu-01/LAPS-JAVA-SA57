package com.laps.backend.services;

import com.laps.backend.models.Employee;
import com.laps.backend.repositories.EmployeeReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeReposity employeeReposity;

    @Autowired
    public EmployeeServiceImpl(EmployeeReposity employeeReposity){
        this.employeeReposity = employeeReposity;
    }

    @Override
    public Optional<Employee> findById(Long id){
        return employeeReposity.findById(id);
    }
}
