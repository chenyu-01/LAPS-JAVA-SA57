package com.laps.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.laps.backend.models.Role;

import jakarta.validation.Valid;

@Service
public interface RoleService {


	List<Role> findAllRoles();

	void createRole(@Valid Role role);

}
