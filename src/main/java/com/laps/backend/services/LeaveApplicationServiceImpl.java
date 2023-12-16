package com.laps.backend.services;

import com.laps.backend.repositories.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveApplicationServiceImpl {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    // Methods for handling leave applications
}

