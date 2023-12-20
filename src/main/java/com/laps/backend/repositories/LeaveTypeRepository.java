package com.laps.backend.repositories;

import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    // Query methods for LeaveType
    @Query("select l from LeaveApplication as l where l.status = :status")
    public Optional<LeaveApplication> findByStatus(@Param("status") String status);
}
