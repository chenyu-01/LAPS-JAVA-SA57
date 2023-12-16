package com.laps.backend.services;

import org.springframework.stereotype.Service;

import com.laps.backend.repositories.LeaveApplicationRepository;

import jakarta.annotation.Resource;
@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService{
@Resource
	private LeaveApplicationRepository leaveapplicationrepository;
	
}
