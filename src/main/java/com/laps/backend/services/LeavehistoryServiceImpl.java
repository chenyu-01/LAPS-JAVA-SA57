package com.laps.backend.services;

import com.laps.backend.models.LeaveApplication;
import com.laps.backend.repositories.LeavehistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class LeavehistoryServiceImpl implements LeavehistoryService{
    @Autowired
    private LeavehistoryRepository leavehistoryRepository;

    @Override
    public List<LeaveApplication> findAllLeavehistorys(){
        return leavehistoryRepository.findAll();
    }

    @Override
    public Optional<LeaveApplication> findById(int id){
        return leavehistoryRepository.findById(id);
    }
    @Override
    public Optional<LeaveApplication> findByStatus(String status){
        return leavehistoryRepository.findByStatus(status);
    }

    @Override
    public LeaveApplication createLeavehistory(LeaveApplication leavehistory){
        return leavehistoryRepository.save(leavehistory);
    }
    @Override
    public LeaveApplication updateLeavehistory(LeaveApplication leavehistory){
        return leavehistoryRepository.save(leavehistory);
    }

    @Override
    public LeaveApplication cancleLeavehistory(LeaveApplication leavehistory){
        return leavehistoryRepository.save(leavehistory);
    }
    @Override
    public void deleteLeavehistory(int id){
        leavehistoryRepository.deleteById(id);
    }
}
