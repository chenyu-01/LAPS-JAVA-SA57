package com.laps.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.laps.backend.models.PublicHolidays;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface PublicHolidayService {

	List<PublicHolidays> getAllPublicHolidays();

	@Transactional
	PublicHolidays createpublicHolidays(PublicHolidays publicHolidays);

	void deletePublicHolidayById(long id);

	Optional<PublicHolidays> findPublicHolidaysById(long id);


	 PublicHolidays changePublicHoliday(PublicHolidays publicHolidays);
}
