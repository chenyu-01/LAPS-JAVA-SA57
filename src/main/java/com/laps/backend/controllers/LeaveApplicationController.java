package com.laps.backend.controllers;

import com.laps.backend.models.*;
import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.LeaveApplicationDTO;
import com.laps.backend.services.EmployeeService;
import com.laps.backend.services.LeaveApplicationService;
import com.laps.backend.services.UserLeaveEntitlementService;
import com.laps.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
public class LeaveApplicationController {
    
    private  final UserService userService;
    private final LeaveApplicationService leaveApplicationService;
    private final EmployeeService employeeService;

    private final UserLeaveEntitlementService userLeaveEntitlementService;





    @Autowired
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService, UserService userService, EmployeeService employeeService, UserLeaveEntitlementService userLeaveEntitlementService) {
        this.leaveApplicationService = leaveApplicationService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.userLeaveEntitlementService = userLeaveEntitlementService;
    }
    // Get all leave applications
    @GetMapping("/applied")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllAppliedApplications() {
        List<LeaveApplication> applications = leaveApplicationService.getAllAppliedApplications();
        if (applications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<LeaveApplicationDTO> applicationDTOS = new ArrayList<>();
        applications.forEach(application -> applicationDTOS.add(new LeaveApplicationDTO(application)));
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
                    .forEach(employee -> applications.addAll(leaveApplicationService.getAppliedApplicationsByEmployee(employee)));

            if (applications.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<LeaveApplicationDTO> applicationDTOS = new ArrayList<>();
            //fliter the application status shall be "Applied" or "Updated"
            applications.stream().filter(application -> application.getStatus().equals("Applied") || application.getStatus().equals("Updated"))
                    .forEach(application -> applicationDTOS.add(new LeaveApplicationDTO(application)));

            return ResponseEntity.ok(applicationDTOS);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveLeaveApplication(@PathVariable Long id) {
        Map<String , Object> response = new HashMap<>();
        try {
            Boolean isApprove =  leaveApplicationService.approveApplication(id);
            if(isApprove){
                return ResponseEntity.ok().build();
            }else{
                response.put("message", "Period conflict with other applications");
                return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Reject leave application
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectLeaveApplication(@PathVariable Long id) {
        try {
            leaveApplicationService.rejectApplication(id);
            addEntitlementBack(leaveApplicationService.findById(id).get());
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

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveApplicationDTO> getApplicationById(@PathVariable("id") Long id) {
        Optional<LeaveApplication> application = leaveApplicationService.findById(id);
        if (application.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        LeaveApplicationDTO applicationDTO = new LeaveApplicationDTO(application.get());
        return ResponseEntity.ok(applicationDTO);
    }

    @GetMapping("/findemployee/{id}")
    public ResponseEntity<?> findEmployeeApplication(@PathVariable("id") Long id){
        Optional<Employee> optEmployee = employeeService.findById(id);
        if (optEmployee.isEmpty()) {
            return new ResponseEntity<>("Employee Not Found",HttpStatus.NOT_FOUND);
        }
        Employee employee = optEmployee.get();
        Optional<List<LeaveApplication>> optLeaveApplications = leaveApplicationService.getEmployeeAllApplications(employee);
        if (optLeaveApplications.isEmpty()){
            return new ResponseEntity<>("No Leave Application Found",HttpStatus.NOT_FOUND);
        }
        List<LeaveApplication> leaveApplications = optLeaveApplications.get();
        List<LeaveApplicationDTO>  leaveApplicationDTOS = new ArrayList<>();
        // filter out deleted applications
        leaveApplications.stream().filter(application -> !application.getStatus().equals("Deleted")).
                forEach(application ->  leaveApplicationDTOS.add(new LeaveApplicationDTO(application)));
        return new ResponseEntity<>(leaveApplicationDTOS,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployeeApplication(@RequestBody @Valid LeaveApplication leaveApplicationBody, BindingResult result) {
        Map<String , Object> response = new HashMap<>();
        if (result.hasErrors()) {
            response.put("message", "Invalid Leave Application");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Long leaveId = leaveApplicationBody.getId();
        Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(leaveId);
        if(optleaveApplication.isEmpty()) {
            response.put("message", "Requested Leave Application Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        LeaveApplication prevApplication = optleaveApplication.get();
        String status = prevApplication.getStatus();
        if (!status.equals("Applied") && !status.equals("Updated")) {
            response.put("message", "Cannot Update Application that is Submitted");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        leaveApplicationBody.setStatus("Updated");
        leaveApplicationBody.setEmployee(prevApplication.getEmployee());
        try {
            addEntitlementBack(prevApplication);
            leaveApplicationService.saveApplication(leaveApplicationBody);
            response.put("message", "Successfully Updated Application");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private void addEntitlementBack(LeaveApplication prevApplication) {
        // add entitlementSubtraction back to userLeaveEntitlement
        UserLeaveEntitlement userLeaveEntitlement = prevApplication.getEmployee().getUserLeaveEntitlement();
        String prevType = prevApplication.getType();
        long entitlementSubtraction = prevApplication.getEntitlementSubtraction();
        if (prevType.equals("Annual")) {
            int prevAnnual = (int) (userLeaveEntitlement.getAnnualEntitledDays() + entitlementSubtraction);
            userLeaveEntitlement.setAnnualEntitledDays(prevAnnual);
        } else if (prevType.equals("Medical")) {
            int prevMedical = (int) (userLeaveEntitlement.getMedicalEntitledDays() + entitlementSubtraction);
            userLeaveEntitlement.setMedicalEntitledDays(prevMedical);
        } else if (prevType.equals("Compensation")) {
            float prevCompensation = userLeaveEntitlement.getCompensationEntitledDays() + entitlementSubtraction;
            userLeaveEntitlement.setCompensationEntitledDays(prevCompensation);
        }
        // save to database
        userLeaveEntitlementService.save(userLeaveEntitlement);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelEmployeeApplication(@PathVariable("id") Long leaveId) {
            Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(leaveId);
            Map<String, Object> response = new HashMap<>();
        if (optleaveApplication.isEmpty()) {
            response.put("message", "Application Not Found");
            return new ResponseEntity<>( response,HttpStatus.NOT_FOUND);
        }
        LeaveApplication leaveApplication = optleaveApplication.get();
        if (!leaveApplication.getStatus().equals("Approved")) {
            response.put("message", "Cannot Cancel Application that is not Approved");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        leaveApplication.setStatus("Cancelled");
        try {
            addEntitlementBack(leaveApplication);
            leaveApplicationService.saveApplication(leaveApplication);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        response.put("message", "Application Successfully Cancelled");
        return new  ResponseEntity<>(response, HttpStatus.OK);

    }


    @DeleteMapping("/delete/{id}")
        public ResponseEntity<?> deleteEmployeeApplication(@PathVariable("id") Long leaveId) {
            Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(leaveId);
            Map<String, Object> response = new HashMap<>();
            if (optleaveApplication.isEmpty()) {
                response.put("message", "Application Not Found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            String status = optleaveApplication.get().getStatus();
            if (!status.equals(("Applied")) && !status.equals("Updated")) {
                response.put("message", "Cannot Delete Application that is not Applied or Updated");
                return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
            }
            LeaveApplication leaveApplication = optleaveApplication.get();
            leaveApplication.setStatus("Deleted");
            try {
                addEntitlementBack(leaveApplication);
                leaveApplicationService.saveApplication(leaveApplication);
            } catch (RuntimeException e) {
                response.put("message", e.getMessage());
                return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
            }
            response.put("message", "Application Successfully Deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submitEmployeeApplication(@PathVariable("id") Long inid, @RequestBody @Valid LeaveApplication leaveApplicationBody, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            // Handle validation errors
            response.put("message", "Invalid Leave Application");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Optional<Employee> optEmployee = employeeService.findById(inid);
        if(optEmployee.isEmpty()){
            response.put("message", "Employee Not Found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        // if no manager, return BAD_REQUEST
        if (optEmployee.get().getManager() == null) {
            response.put("message", "You don't have your manager");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        leaveApplicationBody.setStatus("Applied");
        leaveApplicationBody.setEmployee(optEmployee.get());
        try {
            leaveApplicationService.saveApplication(leaveApplicationBody);
            response.put("message", "Successfully Submitted Application");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/query/{id}")
    public ResponseEntity<List<LeaveApplicationDTO>> queryEmployeeApplication(@PathVariable("id") Long id,@RequestBody String query) {
       Optional<Manager> optManager = Optional.ofNullable(userService.findManagerById(id));
         if (optManager.isEmpty()) {
          return ResponseEntity.noContent().build(); // 204 No Content
         }
        Manager manager = optManager.get();

        String[] keywords = query.split(" ");

        List<LeaveApplication> results = leaveApplicationService.fuzzySearchApplication(keywords).stream()
                .filter(leaveApplication -> leaveApplication.getEmployee().getManager().equals(manager))
                .toList();


        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(results.stream().map(LeaveApplicationDTO::new).collect(Collectors.toList()));
    }


}

