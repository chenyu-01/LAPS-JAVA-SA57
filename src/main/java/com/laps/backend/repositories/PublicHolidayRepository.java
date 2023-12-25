package com.laps.backend.repositories;

import com.laps.backend.models.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.laps.backend.models.PublicHolidays;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PublicHolidayRepository extends JpaRepository<PublicHolidays, Long>{

	PublicHolidays save(PublicHolidays publicHolidays);

    void deleteById(long id);

    @Query("SELECT p FROM PublicHolidays p where p.id = :id")
    Optional<PublicHolidays> findPublicHolidaysById(@Param("id") long id);
}
