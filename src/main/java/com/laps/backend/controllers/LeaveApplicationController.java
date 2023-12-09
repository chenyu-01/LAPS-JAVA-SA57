package com.laps.backend.controllers;

import com.laps.backend.services.LeaveApplicationService;
import com.laps.backend.services.LeaveTypeService;
import com.laps.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leave-applications")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationService leaveApplicationService;

    // RESTful endpoints for managing leave applications
}

