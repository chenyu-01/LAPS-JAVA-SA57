package com.laps.backend.services;

import java.time.Duration;
import java.time.LocalDate;
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

	@Override
	public Duration holidayWeekendDuration(LocalDate startDate, LocalDate endDate){
		List<PublicHolidays> publicHolidays = publicHolidayRepository.findAll();
		Duration duration = Duration.between(startDate, endDate);
		// calculate the duration of public holidays and weekends between startDate and endDate
		while (startDate.isBefore(endDate)) {
			for (PublicHolidays publicHoliday : publicHolidays) {
				// if startDate is a public holiday and also a weekend, avoid counting twice
				if (startDate.getDayOfWeek().getValue() != 6 && startDate.getDayOfWeek().getValue() != 7) {
					break;
				}
				if (startDate.equals(publicHoliday.getDate())) {
					duration = duration.minusDays(1);
					break;
				}
			}
			if (startDate.getDayOfWeek().getValue() == 6 || startDate.getDayOfWeek().getValue() == 7) {
				duration = duration.minusDays(1);
			}
			startDate = startDate.plusDays(1);
		}
		return duration;

	}


	
}
