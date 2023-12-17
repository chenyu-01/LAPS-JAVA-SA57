package com.laps.backend.services;

import org.springframework.stereotype.Service;

import com.laps.backend.repositories.LeaveTypeRepository;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService{

	private LeaveTypeRepository leavetyperepository;
}
