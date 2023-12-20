package com.laps.backend.repositories;

import com.laps.backend.models.Compensation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompensationReposity extends JpaRepository<Compensation,Integer>{
    @Query("select c from Compensation as c where c.type = :type")
    public Optional<Compensation> findByType(@Param("type") String type);
}
