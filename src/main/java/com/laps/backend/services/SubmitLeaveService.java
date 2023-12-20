package com.laps.backend.services;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.LeaveApplicationDTO;
import com.laps.backend.models.SubmitLeaveDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubmitLeaveService {


    void submitLeave(SubmitLeaveDto submitLeaveDto) throws SubmitLeaveServiceImpl.LeaveValidationException;

    void updateLeave(String leaveId, LeaveApplicationDTO leaveApplicationDTO);

    @Transactional
    void updateLeave(String leaveId);

    void deleteLeave(String leaveId);

    void cancelLeave(String leaveId);

    List<LeaveApplication> getAppliedApplicationsByEmployee(Employee employee);


}
