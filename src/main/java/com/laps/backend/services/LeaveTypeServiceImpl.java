package com.laps.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laps.backend.models.LeaveType;
import com.laps.backend.models.LeaveTypeEnum;
import com.laps.backend.repositories.LeaveTypeRepository;

import jakarta.validation.Valid;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService{
	@Autowired
	private LeaveTypeRepository leaveTypeRepository;
	
@Override
@Transactional
public List <LeaveType> getAllLeaveTypes(){
	return leaveTypeRepository.findAll();
}

@Override
@Transactional
public Optional<LeaveType> getLeaveTypeById(int id){
	return leaveTypeRepository.findById(id);
}
public @Valid LeaveType changeLeaveType(@Valid LeaveType leaveType) {
	return leaveTypeRepository.saveAndFlush(leaveType);
}


}

