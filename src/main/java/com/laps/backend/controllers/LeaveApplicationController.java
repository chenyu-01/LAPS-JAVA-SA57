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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Autowired
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService, UserService userService, EmployeeService employeeService) {
        this.leaveApplicationService = leaveApplicationService;
        this.employeeService = employeeService;
        this.userService = userService;
    }
    // Get all leave applications
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

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveApplicationDTO> getApplicationById(@PathVariable("id") Long id) {
        Optional<LeaveApplication> application = leaveApplicationService.findById(id);
        if (application == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        LeaveApplicationDTO applicationDTO = new LeaveApplicationDTO(application.get());
        return ResponseEntity.ok(applicationDTO);
    }

    @GetMapping("/findemployee/{id}")
    public ResponseEntity<?> findEmployeeApplication(@PathVariable("id") Long id){
        Optional<Employee> optEmployee = employeeService.findById(id);
        if (!optEmployee.isPresent()) {
            return new ResponseEntity<String>("Employee Not Found",HttpStatus.NOT_FOUND);
        }
        Employee employee = optEmployee.get();
        Optional<List<LeaveApplication>> optLeaveApplications = leaveApplicationService.getEmployeeAllApplications(employee);
        if (!optLeaveApplications.isPresent()){
            return new ResponseEntity<String>("No Leave Application Found",HttpStatus.NOT_FOUND);
        }
        List<LeaveApplication> leaveApplications = optLeaveApplications.get();
        List<LeaveApplicationDTO>  leaveApplicationDTOS = new ArrayList<>();
        // filter out deleted applications
        leaveApplications.stream().filter(application -> !application.getStatus().equals("Deleted")).
                forEach(application ->  leaveApplicationDTOS.add(new LeaveApplicationDTO(application)));
        return new ResponseEntity<List<LeaveApplicationDTO>>(leaveApplicationDTOS,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployeeApplication(@RequestBody Map<String,String> leaveApplicationBody) throws ParseException {
        Long leaveId = Long.parseLong(leaveApplicationBody.get("leaveId"));
        Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(leaveId);
        if(!optleaveApplication.isPresent()) {
            return new ResponseEntity<String>("Requested Leave Application Not Found",HttpStatus.NOT_FOUND);
        }
        String status = optleaveApplication.get().getStatus();
        if (status.equals("Approved") || status.equals("Rejected") || status.equals("Cancelled")) {
            return new ResponseEntity<String>("Cannot Update Application that is Approved, Rejected or Cancelled", HttpStatus.NOT_ACCEPTABLE);
        }
        LeaveApplication leaveApplication = optleaveApplication.get();
        LocalDateTime startDate = LocalDateTime.parse(leaveApplicationBody.get("startDate"),df);
        LocalDateTime endDate = LocalDateTime.parse(leaveApplicationBody.get("endDate"),df);
        leaveApplication.setStartDate(startDate); //1
        leaveApplication.setEndDate(endDate); //2
        leaveApplication.setType(leaveApplicationBody.get("type")); //3
        leaveApplication.setReason(leaveApplicationBody.get("reason")); //4
        if(leaveApplicationBody.get("contactInfo") == null && leaveApplicationBody.get("isOverseas").equals("true")) {
            return new ResponseEntity<String>("Contact Information is Required for Overseas Leave", HttpStatus.NOT_ACCEPTABLE);
        }
        leaveApplication.setContactInfo(leaveApplicationBody.get("contactInfo")); //5
        leaveApplication.setStatus("Updated"); //6
        leaveApplication.setOverseas(Boolean.parseBoolean(leaveApplicationBody.get("isOverseas"))); //7
        // no need to update employee and comment
        leaveApplicationService.saveApplication(leaveApplication);
        return new ResponseEntity<String>("Successfully Update Application", HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelEmployeeApplication(@PathVariable("id") Long leaveId) throws ParseException {
            Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(leaveId);
            if (optleaveApplication.isPresent()) {
                LeaveApplication leaveApplication = optleaveApplication.get();
                if (!leaveApplication.getStatus().equals("Approved")) {
                    return new ResponseEntity<LeaveApplicationDTO>(HttpStatus.NOT_ACCEPTABLE);
                }
                leaveApplication.setStatus("Cancelled");
                leaveApplicationService.saveApplication(leaveApplication);
                return new  ResponseEntity<String>("Cancelled Application", HttpStatus.OK);
            }
            return new ResponseEntity<String>("Application Not Found" ,HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeApplication(@PathVariable("id") Long leaveId) throws ParseException {
            Optional<LeaveApplication> optleaveApplication = leaveApplicationService.findById(leaveId);
            if (!optleaveApplication.isPresent()) {
                return new ResponseEntity<String>("Application Not Found", HttpStatus.NOT_FOUND);
            }
            String status = optleaveApplication.get().getStatus();
            if (status.equals(("Approved")) || status.equals("Rejected")) {
                return new ResponseEntity<String>("Cannot Delete Application that is Approved Or Rejected", HttpStatus.NOT_ACCEPTABLE);
            }
            LeaveApplication leaveApplication = optleaveApplication.get();
            leaveApplication.setStatus("Deleted");
            leaveApplicationService.saveApplication(leaveApplication);
            return new ResponseEntity<String>("Application Successfully Deleted", HttpStatus.OK);

    }

    @PutMapping("submit/{id}")
    public ResponseEntity<?> submitEmployeeApplication(@PathVariable("id") Long inid,@RequestBody Map<String,String> leaveApplicationBody) throws ParseException {
        Optional<Employee> optEmployee = employeeService.findById(inid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(!optEmployee.isPresent()){
            return new ResponseEntity<String>("Employee Not Found",HttpStatus.NOT_FOUND);
        }
        LeaveApplication leaveApplication = new LeaveApplication();
        LocalDateTime startDate = LocalDateTime.parse(leaveApplicationBody.get("startDate"),df);
        LocalDateTime endDate = LocalDateTime.parse(leaveApplicationBody.get("endDate"),df);
        leaveApplication.setStartDate(startDate);
        leaveApplication.setEndDate(endDate);
        leaveApplication.setType(leaveApplicationBody.get("type"));
        leaveApplication.setComment(leaveApplicationBody.get("comment"));
        leaveApplication.setReason(leaveApplicationBody.get("reason"));
        leaveApplication.setStatus("Applied");
        leaveApplication.setOverseas(Boolean.parseBoolean(leaveApplicationBody.get("isOverseas")));
        if(leaveApplicationBody.get("contactInfo") == null && leaveApplicationBody.get("isOverseas").equals("true")) {
            return new ResponseEntity<String>("Contact Information is Required for Overseas Leave", HttpStatus.NOT_ACCEPTABLE);
        }
        leaveApplication.setContactInfo(leaveApplicationBody.get("contactInfo"));
        leaveApplication.setEmployee(optEmployee.get());
        leaveApplicationService.saveApplication(leaveApplication);
        LeaveApplicationDTO leaveApplicationDTO = new LeaveApplicationDTO(leaveApplication);
        return new ResponseEntity<LeaveApplicationDTO>(leaveApplicationDTO, HttpStatus.OK);
    }


}

