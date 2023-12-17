package com.laps.backend.controllers;

import com.laps.backend.services.LeaveTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leave-types")
public class LeaveTypeController {
    @Autowired
    private LeaveTypeServiceImpl leaveTypeServiceImpl;

    // RESTful endpoints for managing leave types
}
