package com.laps.backend.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.laps.backend.models.LeaveType;
import jakarta.validation.Valid;

@Service
public interface LeaveTypeService {

	List<LeaveType> getAllLeaveTypes();

	Optional<LeaveType> getLeaveTypeById(int id);
	
	@Valid LeaveType changeLeaveType(@Valid LeaveType leaveType);


}
