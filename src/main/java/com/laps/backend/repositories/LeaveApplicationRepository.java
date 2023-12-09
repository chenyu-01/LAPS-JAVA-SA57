package com.laps.backend.repositories;

import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.LeaveType;
import com.laps.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    // Query methods for LeaveApplication
}

