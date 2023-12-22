package com.laps.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.laps.backend.models.PublicHolidays;

@Service
public interface PublicHolidayService {

	List<PublicHolidays> getAllPublicHolidays();

}
