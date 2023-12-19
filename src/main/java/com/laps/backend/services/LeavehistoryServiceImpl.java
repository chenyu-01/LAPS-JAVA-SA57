package com.laps.backend.services;

import com.laps.backend.models.Leavehistory;
import com.laps.backend.repositories.LeavehistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class LeavehistoryServiceImpl implements LeavehistoryService{
    @Autowired
    private LeavehistoryRepository leavehistoryRepository;

    @Override
    public List<Leavehistory> findAllLeavehistorys(){
        return leavehistoryRepository.findAll();
    }

    @Override
    public Optional<Leavehistory> findById(int id){
        return leavehistoryRepository.findById(id);
    }
    @Override
    public Optional<Leavehistory> findByStatus(String status){
        return leavehistoryRepository.findByStatus(status);
    }

    @Override
    public Leavehistory createLeavehistory(Leavehistory leavehistory){
        return leavehistoryRepository.save(leavehistory);
    }
    @Override
    public Leavehistory updateLeavehistory(Leavehistory leavehistory){
        return leavehistoryRepository.save(leavehistory);
    }

    @Override
    public Leavehistory cancleLeavehistory(Leavehistory leavehistory){
        return leavehistoryRepository.save(leavehistory);
    }
    @Override
    public void deleteLeavehistory(int id){
        leavehistoryRepository.deleteById(id);
    }
}
