package com.laps.backend.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laps.backend.models.PublicHolidays;
import com.laps.backend.repositories.PublicHolidayRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublicHolidayServiceImpl implements PublicHolidayService{

	private final PublicHolidayRepository publicHolidayRepository;

	public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository1) {
        this.publicHolidayRepository = publicHolidayRepository1;
    }

	@Override
	@Transactional
	public List<PublicHolidays> getAllPublicHolidays() {
		
		return publicHolidayRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<PublicHolidays> findPublicHolidaysById(long id) {
		return publicHolidayRepository.findPublicHolidaysById(id);
	}

	@Override
	public PublicHolidays changePublicHoliday(PublicHolidays publicHolidays) {
		return publicHolidayRepository.saveAndFlush(publicHolidays);
	}


	@Override
	@Transactional
	public PublicHolidays createpublicHolidays(PublicHolidays publicHolidays) {
		return publicHolidayRepository.saveAndFlush(publicHolidays);
	}

	@Override
	@Transactional
	public void deletePublicHolidayById(long id) {
		// Implement the logic to delete the public holiday by ID
		// For example:
		publicHolidayRepository.deleteById(id);
	}

	public long holidayWeekendDuration(LocalDate startDate, LocalDate endDate){
		List<PublicHolidays> publicHolidays = publicHolidayRepository.findAll();
		long totalDays = ChronoUnit.DAYS.between(startDate, endDate);

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
