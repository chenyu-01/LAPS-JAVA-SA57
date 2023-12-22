package com.laps.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laps.backend.models.PublicHolidays;
import com.laps.backend.repositories.PublicHolidayRepository;

@Service
public class PublicHolidayServiceImpl implements PublicHolidayService{

	@Autowired 
	private PublicHolidayRepository publicHolidayRepository;

	@Override
	public List<PublicHolidays> getAllPublicHolidays() {
		
		return publicHolidayRepository.findAll();
	}


	
}
