package com.laps.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.laps.backend.models.Leave;
import com.laps.backend.repositories.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private LeaveRepository leaveRepository;

//	
	public List<Leave> listAllLeavetypes() {

		return leaveRepository.findAll();
	}

	public Optional<Leave> getLeaveTypeByid(Long id) {

		return leaveRepository.findById(id);
	}

	public void deleteLeaveType(Long Id) {
		leaveRepository.deleteById(Id);
	}
}
