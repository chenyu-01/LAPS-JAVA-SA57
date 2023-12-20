package com.laps.backend.repositories;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long>, JpaSpecificationExecutor<LeaveApplication> {
    // Query methods for LeaveApplication
    List<LeaveApplication> findByStatus(String status);


    List<LeaveApplication> findByEmployee(Employee employee);

}

