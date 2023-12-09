package com.laps.backend.services;

import com.laps.backend.repositories.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    // Methods for handling leave types
}
