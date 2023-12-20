package com.laps.backend.repositories;

import com.laps.backend.models.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    // Query methods for LeaveApplication
    List<LeaveApplication> findByStatus(String status);
}

