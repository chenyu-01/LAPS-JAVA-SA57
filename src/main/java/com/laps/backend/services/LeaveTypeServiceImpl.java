package com.laps.backend.services;

import com.laps.backend.repositories.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
<<<<<<<< HEAD:src/main/java/com/laps/backend/services/LeaveService.java
public interface LeaveTypeService {
  
========
public class LeaveTypeServiceImpl {
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
>>>>>>>> origin/main:src/main/java/com/laps/backend/services/LeaveTypeServiceImpl.java

    // Methods for handling leave types
}
