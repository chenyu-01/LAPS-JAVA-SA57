package com.laps.backend.repositories;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    // Query methods for LeaveApplication
    List<LeaveApplication> findByStatus(String status);

    @Query("select l from LeaveApplication as l where l.employee = :employee_id and l.id = :id")
    Optional<LeaveApplication> findById(@Param("id")Long id, @Param("employee_id")Long employee_id);

    @Query("select e from Employee as e where e.id = :id")
    Optional<Employee> findEmployeeById(@Param("id") Long id);
}

