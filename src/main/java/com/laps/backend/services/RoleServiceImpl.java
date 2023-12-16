package com.laps.backend.services;

import java.util.List;

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
	rolerepository.findAll();
	return null;
}

@Override
public void createRole(@Valid Role role) {
	// TODO Auto-generated method stub
	
}
}
