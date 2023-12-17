package com.laps.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.laps.backend.models.Role;

import jakarta.validation.Valid;

@Service
public interface RoleService {


	List<Role> findAllRoles();

	Role createRole(@Valid Role role);

	Optional<Role> findRole(String id);

	@Valid Role changeRole(@Valid Role role);

	void removeRole(Optional<Role> role);

	List<Role> findRoleByName(String name);

	List<String> findAllRolesNames();

}
