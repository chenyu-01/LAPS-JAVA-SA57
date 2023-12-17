package com.laps.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import com.laps.backend.models.Role;
import com.laps.backend.models.User;
import com.laps.backend.repositories.UserRepository;
import com.laps.backend.repositories.RoleRepository;


@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class LapsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository rolerepository) {
		return args -> {
			
			// Add a few Roles
		  Role admin= rolerepository.save(new Role("Admin", "Administrator", "System administrator"));
		  Role Employee = rolerepository.save(new Role("Employee", "Staff", "Staff members"));
		  Role Manager = rolerepository.save(new Role("Manager", "Manager", "Manager"));

			User user = new User();
			user.setEmail("testuser@gmail.com");
			user.setPassword("yYjHDp)d~+]Pb6");
			user.setName("Test User");
			user.setRole("Admin");
			userRepository.save(user);
		};
	}
	
}