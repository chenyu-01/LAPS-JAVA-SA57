package com.laps.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laps.backend.models.Role;
import com.laps.backend.repositories.RoleRepository;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleRepository rolerepository;

	@Override
	@Transactional
	public List<Role> findAllRoles() {
		return rolerepository.findAll();
	}

	@Transactional
	@Override
	public Role createRole(@Valid Role role) {
		return rolerepository.saveAndFlush(role);

	}

	@Transactional
	@Override
	public Optional<Role> findRole(String id) {
		// TODO Auto-generated method stub
		return rolerepository.findById(id);
	}

	@Transactional
	@Override
	public @Valid Role changeRole(@Valid Role role) {
		// TODO Auto-generated method stub
		return rolerepository.saveAndFlush(role);

	}

	@Transactional
	@Override
	public void removeRole(Optional<Role> role) {
		// TODO Auto-generated method stub
		if (role != null) {
			rolerepository.delete(role.get());
		}
	}

	@Transactional
	@Override
	public List<Role> findRoleByName(String name) {
		return rolerepository.findRoleByName(name);
	}

	@Transactional
	@Override
	public List<String> findAllRolesNames() {
		return rolerepository.findAllRolesNames();
	}
}
