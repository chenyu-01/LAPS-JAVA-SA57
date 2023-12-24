package com.laps.backend.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laps.backend.models.PublicHolidays;
import com.laps.backend.repositories.PublicHolidayRepository;

@Service
public class PublicHolidayServiceImpl implements PublicHolidayService{

	private final PublicHolidayRepository publicHolidayRepository;

	public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository1) {
        this.publicHolidayRepository = publicHolidayRepository1;
    }

	@Override
	public List<PublicHolidays> getAllPublicHolidays() {
		
		return publicHolidayRepository.findAll();
	}

	@Override
	public long holidayWeekendDuration(LocalDate startDate, LocalDate endDate){
		List<PublicHolidays> publicHolidays = publicHolidayRepository.findAll();
		long totalDays = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));

		// calculate the duration of public holidays and weekends between startDate and endDate
		while (startDate.isBefore(endDate)) {
			for (PublicHolidays publicHoliday : publicHolidays) {
				// if startDate is a public holiday and also a weekend, avoid counting twice
				if (startDate.getDayOfWeek().getValue() != 6 && startDate.getDayOfWeek().getValue() != 7) {
					break;
				}
				if (startDate.equals(publicHoliday.getDate())) {
					totalDays -= 1;
					break;
				}
			}
			if (startDate.getDayOfWeek().getValue() == 6 || startDate.getDayOfWeek().getValue() == 7) {
				totalDays -= 1;
			}
			startDate = startDate.plusDays(1);
		}
		return totalDays;

	}
}
