package com.laps.backend.repositories;


import com.laps.backend.models.Leavehistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LeavehistoryRepository extends JpaRepository<Leavehistory,Integer> {

    @Query("select l from Leavehistory as l where l.status = :status")
    public Optional<Leavehistory> findByStatus(@Param("status") String status);

}
