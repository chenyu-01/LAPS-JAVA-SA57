package com.laps.backend.controllers;

import com.laps.backend.models.*;
import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.LeaveApplicationDTO;
import com.laps.backend.repositories.EmployeeReposity;
import com.laps.backend.services.EmployeeService;
import com.laps.backend.services.EmployeeServiceImpl;
import com.laps.backend.services.EmployeeService;
import com.laps.backend.services.LeaveApplicationService;
import com.laps.backend.services.UserService;
import com.laps.backend.services.LeaveApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/applications")
public class LeaveApplicationController {
    
    private  final UserService userService;
    private final LeaveApplicationService leaveApplicationService;
    private final EmployeeService employeeService;

    @Autowired
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService, UserService userService, EmployeeService employeeService) {
        this.leaveApplicationService = leaveApplicationService;
        this.employeeService = employeeService;
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

    @GetMapping("/approved/{id}")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllApprovedApplications(@PathVariable("id") Long id) {
        Optional<Employee> optEmployee= employeeService.findById(id);
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

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findEmployeeApplication(@PathVariable("id") Long id){
        Optional<Employee> optEmployee = employeeService.findById(id);
        if (optEmployee.isPresent()){
            Employee employee = optEmployee.get();
            Optional<List<LeaveApplication>> optLeaveApplications = leaveApplicationService.getEmployeeAllApplications(employee);
            if (optLeaveApplications.isPresent()){
                List<LeaveApplication> leaveApplications = optLeaveApplications.get();
                List<LeaveApplicationDTO>  leaveApplicationDTOS = new ArrayList<>();
                leaveApplications.stream().forEach(application ->  leaveApplicationDTOS.add(new LeaveApplicationDTO(application)));
                return new
                        ResponseEntity<List<LeaveApplicationDTO>>(leaveApplicationDTOS,HttpStatus.OK);

            }else {
                return new
                        ResponseEntity<List<LeaveApplicationDTO>>(HttpStatus.NOT_FOUND);
            }
        }else {
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployeeApplication(@PathVariable("id") Long inid,@RequestBody Map<String,String> leaveApplicationBody) throws ParseException {
        Optional<Employee> optEmployee = employeeService.findById(inid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(optEmployee.isPresent()){

            Long id = Long.parseLong(leaveApplicationBody.get("id"));
            Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(id,inid);
            if (optleaveApplication.isPresent()) {
                LeaveApplication leaveApplication = optleaveApplication.get();
                Date startDate = sdf.parse(leaveApplicationBody.get("startDate"));
                Date endDate = sdf.parse(leaveApplicationBody.get("endDate"));
                leaveApplication.setStartDate(startDate);
                leaveApplication.setEndDate(endDate);
                leaveApplication.setType(leaveApplicationBody.get("type"));
                leaveApplication.setComment(leaveApplicationBody.get("comment"));
                leaveApplication.setReason(leaveApplicationBody.get("reason"));
                leaveApplicationService.saveApplication(leaveApplication);
                LeaveApplicationDTO leaveApplicationDTO = new LeaveApplicationDTO(leaveApplication);
                return new
                        ResponseEntity<LeaveApplicationDTO>(leaveApplicationDTO, HttpStatus.OK);
            }else{
                return new
                        ResponseEntity<LeaveApplicationDTO>(HttpStatus.NOT_FOUND);
            }

        }else {
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelEmployeeApplication(@PathVariable("id") Long inid,@RequestBody Map<String,String> leaveApplicationBody) throws ParseException {

        Optional<Employee> optEmployee = employeeService.findById(inid);
        if(optEmployee.isPresent()){

            Long id = Long.parseLong(leaveApplicationBody.get("id"));
            Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(id,inid);
            if (optleaveApplication.isPresent()) {
                LeaveApplication leaveApplication = optleaveApplication.get();
                leaveApplication.setStatus("Cancelled");
                leaveApplicationService.saveApplication(leaveApplication);
                LeaveApplicationDTO leaveApplicationDTO = new LeaveApplicationDTO(leaveApplication);
                return new
                        ResponseEntity<LeaveApplicationDTO>(leaveApplicationDTO, HttpStatus.OK);
            }else{
                return new
                        ResponseEntity<LeaveApplicationDTO>(HttpStatus.NOT_FOUND);
            }

        }else {
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }


    }


    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeApplication(@PathVariable("id") Long inid,@RequestBody Map<String,String> leaveApplicationBody) throws ParseException {
        Optional<Employee> optEmployee = employeeService.findById(inid);
        if(optEmployee.isPresent()){

            Long id = Long.parseLong(leaveApplicationBody.get("id"));
            Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(id,inid);
            if (optleaveApplication.isPresent()) {
                LeaveApplication leaveApplication = optleaveApplication.get();
                leaveApplication.setStatus("Deleted");
                leaveApplicationService.saveApplication(leaveApplication);
                LeaveApplicationDTO leaveApplicationDTO = new LeaveApplicationDTO(leaveApplication);
                return new
                        ResponseEntity<LeaveApplicationDTO>(leaveApplicationDTO, HttpStatus.OK);
            }else{
                return new
                        ResponseEntity<LeaveApplicationDTO>(HttpStatus.NOT_FOUND);
            }

        }else {
            return new
                    ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }


    }


}

