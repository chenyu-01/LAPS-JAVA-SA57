package com.laps.backend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.laps.backend.models.PublicHolidays;
import com.laps.backend.repositories.PublicHolidayRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublicHolidayServiceImpl implements PublicHolidayService{

	@Autowired 
	private PublicHolidayRepository publicHolidayRepository;

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

}
