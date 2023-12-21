package com.laps.backend.repositories;

import com.laps.backend.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReposity extends JpaRepository<Employee, Long> {

}
