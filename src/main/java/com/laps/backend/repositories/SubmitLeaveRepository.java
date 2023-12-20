package com.laps.backend.repositories;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubmitLeaveRepository extends JpaRepository<LeaveApplication, Long> {
    // You can add custom query methods if needed
    List<LeaveApplication> findByEmployee(Employee employee);

}
