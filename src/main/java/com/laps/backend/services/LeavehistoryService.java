package com.laps.backend.services;

import com.laps.backend.models.LeaveApplication;

import java.util.List;
import java.util.Optional;

public interface LeavehistoryService {
    List<LeaveApplication> findAllLeavehistorys();
    Optional<LeaveApplication> findByStatus(String status);
    Optional<LeaveApplication> findById(int id);
    LeaveApplication createLeavehistory(LeaveApplication leavehistory);
    LeaveApplication updateLeavehistory(LeaveApplication leavehistory);
    LeaveApplication cancleLeavehistory(LeaveApplication leavehistory);
    void deleteLeavehistory(int id);
}
