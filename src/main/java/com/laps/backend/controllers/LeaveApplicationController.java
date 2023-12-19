package com.laps.backend.controllers;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.LeaveApplicationDTO;
import com.laps.backend.services.LeaveApplicationService;
import com.laps.backend.services.LeaveApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationServiceImpl leaveApplicationServiceImpl;

    private final LeaveApplicationService leaveApplicationService;

    @Autowired
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    @GetMapping("/applied")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllAppliedApplications() {
        List<LeaveApplication> applications = leaveApplicationService.getAllAppliedApplications();
        if (applications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<LeaveApplicationDTO> applicationDTOS = new ArrayList<>();
        applications.stream().forEach(application -> applicationDTOS.add(new LeaveApplicationDTO(application)));
        return ResponseEntity.ok(applicationDTOS);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllApprovedApplications() {
        List<LeaveApplication> applications = leaveApplicationService.getAllApprovedApplications();
        if (applications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<LeaveApplicationDTO> applicationDTOS = new ArrayList<>();
        applications.stream().forEach(application -> applicationDTOS.add(new LeaveApplicationDTO(application)));
        return ResponseEntity.ok(applicationDTOS);
    }

    @GetMapping("/Rejected")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllRejectedApplications() {
        List<LeaveApplication> applications = leaveApplicationService.getAllApprovedApplications();
        if (applications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<LeaveApplicationDTO> applicationDTOS = new ArrayList<>();
        applications.stream().forEach(application -> applicationDTOS.add(new LeaveApplicationDTO(application)));
        return ResponseEntity.ok(applicationDTOS);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveLeaveApplication(@PathVariable Long id) {
        try {
            leaveApplicationService.approveApplication(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Reject leave application
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectLeaveApplication(@PathVariable Long id) {
        try {
            leaveApplicationService.rejectApplication(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Add comment to leave application
    @PutMapping("/comment/{id}")
    public ResponseEntity<?> addCommentToLeaveApplication(@PathVariable Long id, @RequestBody Map<String, String> comment) {
        try {
            String commentTmp = comment.get("comment");
            leaveApplicationService.addCommentToApplication(id, commentTmp);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/leaveApplication/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id, @RequestBody Employee employee){
        Optional<Employee> optEmployee = leaveApplicationService.findEmployeeById(employee.getId());
        if (optEmployee.isPresent()){
            Optional<LeaveApplication> optLeaveApplication = leaveApplicationService.findById(id,employee.getId());
            if (optLeaveApplication.isPresent()){
                LeaveApplication leaveApplication = optLeaveApplication.get();
                return new
                        ResponseEntity<LeaveApplication>(leaveApplication,HttpStatus.OK);
            }else{
                return new
                        ResponseEntity<LeaveApplication>(HttpStatus.NOT_FOUND);
            }
        }else{
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }




}

