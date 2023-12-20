package com.laps.backend.controllers;

import com.laps.backend.models.*;
import com.laps.backend.services.LeaveApplicationService;
import com.laps.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class LeaveApplicationController {

    private  final UserService userService;
    private final LeaveApplicationService leaveApplicationService;

    @Autowired
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService,UserService userService) {
        this.leaveApplicationService = leaveApplicationService;
        this.userService = userService;
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

    @GetMapping("/applied_list/{id}")
    public ResponseEntity<List<LeaveApplicationDTO>> getApplicationListByManagerID(@PathVariable Long id) {
        try {//check if manager exists
            Manager manager = userService.findManagerById(id);
            if (manager == null) {
                return ResponseEntity.badRequest().body(null);
            }

            List<LeaveApplication> applications = new ArrayList<>();
            userService.findAllEmployeeByManager(manager)
                    .stream()
                    .forEach(employee -> applications.addAll(leaveApplicationService.getAppliedApplicationsByEmployee(employee)));

            if (applications.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<LeaveApplicationDTO> applicationDTOS = new ArrayList<>();
            applications.stream().forEach(application -> applicationDTOS.add(new LeaveApplicationDTO(application)));
            return ResponseEntity.ok(applicationDTOS);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
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
    @GetMapping("/search")
    public ResponseEntity<?> searchApplication(@RequestBody Map<String, String> search) {
        //check request body
        if (search.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request body");
        }

        String[] keywords = search.get("keywords").toLowerCase().split(" ");

        List<LeaveApplication> applications = leaveApplicationService.fuzzySearchApplication(keywords);

        //find all applications that match the keywords about Employee's name
        //List<Employee> employees = userService.searchUser(keywords);

        return ResponseEntity.ok(applications);
    }

}

