package com.laps.backend;

import com.laps.backend.models.*;
import com.laps.backend.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class LapsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, LeaveApplicationRepository leaveRepository) {
		return args -> {

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
			for (int i = 0; i < 10; i++){
				LeaveApplication leaveApplication1 = new LeaveApplication();
				leaveApplication1.setEmployee((Employee) user2);
				leaveApplication1.setStartDate(Date.from(new Date().toInstant().plusSeconds(86400))); // tomorrow
				leaveApplication1.setEndDate(Date.from(new Date().toInstant().plusSeconds(86400 * 2))); // day after tomorrow
				leaveApplication1.setReason("Sick");
				leaveApplication1.setStatus("Applied");
				leaveApplication1.setType("Annual");
				leaveRepository.save(leaveApplication1);
			}


			User user4 = new Admin();
			user4.setEmail("testuser2@gmail.com");
			user4.setPassword("password123");
			user4.setName("Test User 2");
			user4.setRole("Admin");
			userRepository.save(user4);


		};
	}
}
