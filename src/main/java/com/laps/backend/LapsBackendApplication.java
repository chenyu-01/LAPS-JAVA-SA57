package com.laps.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.laps.backend.models.Role;
import com.laps.backend.models.LeaveType;
import com.laps.backend.models.LeaveTypeEnum;
import com.laps.backend.models.User;
import com.laps.backend.repositories.UserRepository;
import com.laps.backend.repositories.RoleRepository;
import com.laps.backend.repositories.LeaveTypeRepository;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LapsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository rolerepository,
			LeaveTypeRepository leaveTypeRepository) {
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
			
			// Add users
			User user = new User();
			user.setEmail("testuser@gmail.com");
			user.setPassword("yYjHDp)d~+]Pb6");
			user.setName("Test User");
			user.setRole("Admin");
			userRepository.save(user);

		};
	}

}