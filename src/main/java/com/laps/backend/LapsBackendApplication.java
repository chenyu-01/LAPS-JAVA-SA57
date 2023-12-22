package com.laps.backend;

import com.laps.backend.models.*;
import com.laps.backend.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.laps.backend.repositories.UserRepository;
import com.laps.backend.repositories.RoleRepository;
import com.laps.backend.repositories.LeaveTypeRepository;
import com.laps.backend.repositories.PublicHolidayRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class LapsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository rolerepository,
			LeaveTypeRepository leaveTypeRepository, LeaveApplicationRepository leaveRepository,PublicHolidayRepository publicHolidayRepository) {

		return args -> {

			// Add a few Roles
			Role admin = rolerepository.save(new Role("Admin", "Administrator", "System administrator"));
			Role Employee = rolerepository.save(new Role("Employee", "Staff", "Staff members"));
			Role Manager = rolerepository.save(new Role("Manager", "Manager", "Manager"));

			// Add Leave Type
			LeaveType lt1 = leaveTypeRepository.save(new LeaveType("Admin",LeaveTypeEnum.MEDICAL, 60));
			LeaveType lt2 = leaveTypeRepository.save(new LeaveType("Admin",LeaveTypeEnum.ANNUAL, 18));
			LeaveType lt3 = leaveTypeRepository.save(new LeaveType("Admin",LeaveTypeEnum.COMPENSATION, 10));

			LeaveType lt4 = leaveTypeRepository.save(new LeaveType("Employee",LeaveTypeEnum.MEDICAL, 60));
			LeaveType lt5 = leaveTypeRepository.save(new LeaveType("Employee",LeaveTypeEnum.ANNUAL, 14));
			LeaveType lt6 = leaveTypeRepository.save(new LeaveType("Employee",LeaveTypeEnum.COMPENSATION, 10));

			LeaveType lt7 = leaveTypeRepository.save(new LeaveType("Manager",LeaveTypeEnum.MEDICAL, 60));
			LeaveType lt8 = leaveTypeRepository.save(new LeaveType("Manager",LeaveTypeEnum.ANNUAL, 14));
			LeaveType lt9 = leaveTypeRepository.save(new LeaveType("Manager",LeaveTypeEnum.COMPENSATION, 10));

			User user1 = new Manager();
			user1.setEmail("testuser3@gmail.com");
			user1.setPassword("password123");
			user1.setName("Test User 3");
			user1.setRole("Manager");
			userRepository.save(user1); // persist manager to database first
			User user2 = new Employee();
			user2.setEmail("testuser@gmail.com");
			user2.setPassword("yYjHDp)d~+]Pb6");
			user2.setName("Employee1");
			user2.setRole("Employee");
			((Employee) user2).setManager((Manager) user1);
			userRepository.save(user2);
			User user3 = new Employee();
			user3.setEmail("testuser1@gmail.com");
			user3.setPassword("password123");
			user3.setName("Employee2");
			user3.setRole("Employee");
			((Employee) user3).setManager((Manager) user1);
			userRepository.save(user3);

			List<Employee> employees = List.of((Employee) user2, (Employee) user3);
			((Manager) user1).setSubordinates(employees);
			userRepository.save(user1);
			for (int i = 0; i < 5; i++){
				LeaveApplication leaveApplication1 = new LeaveApplication();
				leaveApplication1.setEmployee((Employee) user2);
				leaveApplication1.setStartDate(LocalDateTime.now()); // today
				leaveApplication1.setEndDate(LocalDateTime.now().plusDays(1)); // tomorrow
				leaveApplication1.setReason("Sick");
				leaveApplication1.setStatus("Applied");
				leaveApplication1.setType("Annual");
				leaveApplication1.setIsOverseas(false);
				leaveRepository.save(leaveApplication1);
			}

			for (int i = 0 ; i < 5; i++){
				LeaveApplication leaveApplication2 = new LeaveApplication();
				leaveApplication2.setEmployee((Employee) user3);
				leaveApplication2.setStartDate(LocalDateTime.now().plusDays(1)); // tomorrow
				leaveApplication2.setEndDate(LocalDateTime.now().plusDays(2)); // day after tomorrow
				leaveApplication2.setReason("Sick");
				leaveApplication2.setStatus("Approved");
				leaveApplication2.setType("Annual");
				leaveApplication2.setIsOverseas(false);
				leaveRepository.save(leaveApplication2);
			}


			User user4 = new Admin();
			user4.setEmail("testuser2@gmail.com");
			user4.setPassword("password123");
			user4.setName("Test User 2");
			user4.setRole("Admin");
			userRepository.save(user4);

			PublicHolidays P1 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-01-01"), "New Year"));
			PublicHolidays P2 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-02-10"), "Chinese New Year"));
			PublicHolidays P3 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-02-11"), "Chinese New Year"));
			PublicHolidays P4 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-02-12"), "Chinese New Year"));
			PublicHolidays P5 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-03-29"), "Good Friday"));
			PublicHolidays P6 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-04-10"), "Hari Raya Pusa"));
			PublicHolidays P7 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-05-01"), "Labour Day"));
			PublicHolidays P8 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-05-23"), "Vesak Day"));
			PublicHolidays P9 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-06-16"), "Hari Raya Haji"));
			PublicHolidays P10 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-07-09"), "Natioanl Day"));
			PublicHolidays P11 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2024-10-31"), "Deepavali "));
			PublicHolidays P12 = publicHolidayRepository
					.save(new PublicHolidays(LocalDate.parse("2023-12-25"), "Chirstmas Day"));

		};
	}

}