package com.laps.backend.services;

import com.laps.backend.repositories.LeaveApplicationRepository;
import com.laps.backend.repositories.LeaveTypeRepository;
import com.laps.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveApplicationService {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    // Methods for handling leave applications
}

