package com.laps.backend;

import com.laps.backend.services.PublicHolidayServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.laps.backend.models.PublicHolidays;
import com.laps.backend.repositories.PublicHolidayRepository;

class PublicHolidayServiceImplTest {

    @Mock
    private PublicHolidayRepository publicHolidayRepository;

    @InjectMocks
    private PublicHolidayServiceImpl publicHolidayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<PublicHolidays> mockHolidays = Arrays.asList(
                new PublicHolidays(LocalDate.of(2023, 12, 25), "testday1")
                // Example public holiday
        );

        when(publicHolidayRepository.findAll()).thenReturn(mockHolidays);
    }

    @Test
    void testWeekendsOnly() {
        LocalDate startDate = LocalDate.of(2023, 12, 23); // Saturday
        LocalDate endDate = LocalDate.of(2023, 12, 24);   // Sunday
        long duration = publicHolidayService.holidayWeekendDuration(startDate, endDate);
        assertEquals(0, duration, "Duration should be 0 for weekends only");
    }
    @Test
    void testHolidaysOnly() {
        LocalDate startDate = LocalDate.of(2023, 12, 25); // Public holiday
        LocalDate endDate = LocalDate.of(2023, 12, 26);   // Public holiday
        long duration = publicHolidayService.holidayWeekendDuration(startDate, endDate);
        assertEquals(1, duration, "Duration should be 0 for holidays only");
    }
    @Test
    void testWeekendsHolidays() {
        LocalDate startDate = LocalDate.of(2023, 12, 23); // Saturday
        LocalDate endDate = LocalDate.of(2023, 12, 26);   // Public holiday
        long duration = publicHolidayService.holidayWeekendDuration(startDate, endDate);
        assertEquals(1, duration, "Duration should be 1 for weekends and holidays");
    }

    @Test
    void testNoWeekendsHolidays() {
        LocalDate startDate = LocalDate.of(2023, 12, 26); // Sunday
        LocalDate endDate = LocalDate.of(2023, 12, 28);   // Public holiday
        long duration = publicHolidayService.holidayWeekendDuration(startDate, endDate);
        assertEquals(3, duration, "Duration should be 3 days");
    }

    // Add other test methods for each scenario here
}
