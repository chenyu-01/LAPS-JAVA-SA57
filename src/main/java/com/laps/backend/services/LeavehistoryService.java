package com.laps.backend.services;

import com.laps.backend.models.Leavehistory;

import java.util.List;
import java.util.Optional;

public interface LeavehistoryService {
    List<Leavehistory> findAllLeavehistorys();
    Optional<Leavehistory> findByStatus(String status);
    Optional<Leavehistory> findById(int id);
    Leavehistory createLeavehistory(Leavehistory leavehistory);
    Leavehistory updateLeavehistory(Leavehistory leavehistory);
    Leavehistory cancleLeavehistory(Leavehistory leavehistory);
    void deleteLeavehistory(int id);
}
