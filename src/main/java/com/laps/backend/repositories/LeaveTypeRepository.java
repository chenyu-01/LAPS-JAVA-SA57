package com.laps.backend.repositories;

import com.laps.backend.models.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    // Query methods for LeaveType
}
