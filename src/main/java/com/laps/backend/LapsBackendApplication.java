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

			User user3 = new Manager();
			user3.setEmail("testuser3@gmail.com");
			user3.setPassword("password123");
			user3.setName("Test User 3");
			user3.setRole("Manager");
			userRepository.save(user3); // persist manager to database first
			User user = new Employee();
			user.setEmail("testuser@gmail.com");
			user.setPassword("yYjHDp)d~+]Pb6");
			user.setName("Employee1");
			user.setRole("Employee");
			((Employee) user).setManager((Manager) user3);
			userRepository.save(user);
			User user1 = new Employee();
			user1.setEmail("testuser1@gmail.com");
			user1.setPassword("password123");
			user1.setName("Employee2");
			user1.setRole("Employee");
			((Employee) user1).setManager((Manager) user3);
			userRepository.save(user1);

			List<Employee> employees = List.of((Employee) user, (Employee) user1);
			((Manager) user3).setSubordinates(employees);
			userRepository.save(user3);
			for (int i = 0; i < 10; i++){
				LeaveApplication leaveApplication1 = new LeaveApplication();
				leaveApplication1.setEmployee((Employee) user);
				leaveApplication1.setStartDate(Date.from(new Date().toInstant().plusSeconds(86400))); // tomorrow
				leaveApplication1.setEndDate(Date.from(new Date().toInstant().plusSeconds(86400 * 2))); // day after tomorrow
				leaveApplication1.setReason("Sick");
				leaveApplication1.setStatus("Applied");
				leaveRepository.save(leaveApplication1);
			}


			User user2 = new Admin();
			user2.setEmail("testuser2@gmail.com");
			user2.setPassword("password123");
			user2.setName("Test User 2");
			user2.setRole("Admin");
			userRepository.save(user2);


		};
	}
}
