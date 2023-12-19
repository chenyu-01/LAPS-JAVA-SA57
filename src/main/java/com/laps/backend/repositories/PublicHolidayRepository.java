package com.laps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laps.backend.models.PublicHolidays;

public interface PublicHolidayRepository extends JpaRepository<PublicHolidays, Long>{

	PublicHolidays save(PublicHolidays publicHolidays);
}
