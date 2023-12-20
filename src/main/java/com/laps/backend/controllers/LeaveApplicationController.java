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

    @GetMapping("/approved/{id}")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllApprovedApplications(@PathVariable("id") Long id) {
        Optional<Employee> optEmployee= leaveApplicationService.findEmployeeById(id);
        if(optEmployee.isPresent()){

        }
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
        List<LeaveApplication> applications = leaveApplicationService.getAllRejectedApplications();
        if (applications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<LeaveApplicationDTO> applicationDTOS = new ArrayList<>();
        applications.stream().forEach(application -> applicationDTOS.add(new LeaveApplicationDTO(application)));
        return ResponseEntity.ok(applicationDTOS);
    }

    @GetMapping("/Canceled")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllCanceledApplications() {
        List<LeaveApplication> applications = leaveApplicationService.getAllCanceledApplications();
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



    @PutMapping("/find/{id}")
    public ResponseEntity<?> findEmployeeApplication(@PathVariable("id") Long id){
        Optional<Employee> optEmployee = leaveApplicationService.findEmployeeById(id);
        if (optEmployee.isPresent()){
            Employee employee = optEmployee.get();
            Optional<List<LeaveApplication>> optLeaveApplications = leaveApplicationService.getEmployeeAllApplications(employee);
            if (optLeaveApplications.isPresent()){
                List<LeaveApplication> leaveApplications = optLeaveApplications.get();
                return new
                        ResponseEntity<List<LeaveApplication>>(leaveApplications,HttpStatus.OK);
            }else {
                return new
                        ResponseEntity<List<LeaveApplication>>(HttpStatus.NOT_FOUND);
            }
            }else {
                return new
                        ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployeeApplication(@PathVariable("id") Long id,@RequestBody LeaveApplication inLeaveApplication){
        Optional<Employee> optEmployee = leaveApplicationService.findEmployeeById(id);
        if(optEmployee.isPresent()){
            LeaveApplication leaveApplication = leaveApplicationService.saveApplication(inLeaveApplication);
            return new
                    ResponseEntity<LeaveApplication>(leaveApplication,HttpStatus.OK);

        }else {
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelLeaveApplication(@PathVariable("id") Long id,
                                                                   @RequestBody LeaveApplication inLeaveApplication){
        Optional<Employee> optEmployee = leaveApplicationService.findEmployeeById(id);
        if(optEmployee.isPresent()){
            inLeaveApplication.setStatus("Canceled");
            LeaveApplication leaveApplication = leaveApplicationService.saveApplication(inLeaveApplication);
            return new
                    ResponseEntity<LeaveApplication>(leaveApplication,HttpStatus.OK);
        }else {
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteLeaveApplication(@PathVariable("id") Long id,
                                                                   @RequestBody LeaveApplication inLeaveApplication){
        Optional<Employee> optEmployee = leaveApplicationService.findEmployeeById(id);
        if(optEmployee.isPresent()){
            inLeaveApplication.setStatus("Deleted");
            LeaveApplication leaveApplication = leaveApplicationService.saveApplication(inLeaveApplication);
            return new
                    ResponseEntity<LeaveApplication>(leaveApplication,HttpStatus.OK);
        }else {
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }






}

