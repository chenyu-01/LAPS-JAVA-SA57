package com.laps.backend.services;

import com.laps.backend.repositories.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

@Service
public class LeaveApplicationServiceImpl{
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;
}