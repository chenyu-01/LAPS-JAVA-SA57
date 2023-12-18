package com.laps.backend;

import com.laps.backend.models.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.laps.backend.models.User;
import com.laps.backend.repositories.UserRepository;
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class LapsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository) {
		return args -> {
			User user = new Employee();
			user.setEmail("testuser@gmail.com");
			user.setPassword("yYjHDp)d~+]Pb6");
			user.setName("Test User");
			user.setRole("Employee");
			userRepository.save(user);
		};
	}
}
