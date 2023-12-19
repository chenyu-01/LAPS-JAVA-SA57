package com.laps.backend.repositories;


import com.laps.backend.models.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LeavehistoryRepository extends JpaRepository<LeaveApplication,Integer> {

    @Query("select l from LeaveApplication as l where l.status = :status")
    public Optional<LeaveApplication> findByStatus(@Param("status") String status);

}
