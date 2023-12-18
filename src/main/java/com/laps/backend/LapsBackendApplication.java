package com.laps.backend;

import com.laps.backend.models.*;
import com.laps.backend.repositories.LeaveApplicationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.laps.backend.repositories.UserRepository;
import java.util.Date;
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class LapsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, LeaveApplicationRepository leaveRepository) {
		return args -> {
			Employee user = new Employee();
			user.setEmail("testuser@gmail.com");
			user.setPassword("yYjHDp)d~+]Pb6");
			user.setName("Test User");
			user.setRole("Employee");
			userRepository.save(user);

			for (int i = 0; i < 10; i++){
				LeaveApplication leaveApplication1 = new LeaveApplication();
				leaveApplication1.setEmployee(user);
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

			User user3 = new Manager();
			user3.setEmail("testuser3@gmail.com");
			user3.setPassword("password123");
			user3.setName("Test User 3");
			user3.setRole("Manager");
			userRepository.save(user3);
		};
	}
}
