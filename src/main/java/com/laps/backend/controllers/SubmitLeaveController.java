package com.laps.backend.controllers;

import com.laps.backend.models.LeaveApplicationDTO;
import com.laps.backend.models.SubmitLeaveDto;
import com.laps.backend.models.UserDTO;
import com.laps.backend.services.LeaveApplicationService;
import com.laps.backend.services.SubmitLeaveService;
import com.laps.backend.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import validator.SubmitLeaveValidator;

@RestController
@RequestMapping("/leave")
public class SubmitLeaveController {

    //private  final UserService userService;
    private SubmitLeaveService submitLeaveService;

    @Autowired
    public SubmitLeaveController(LeaveApplicationService leaveApplicationService, UserService userService) {
        this.submitLeaveService = submitLeaveService;
        //this.userService = userService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitLeave(@RequestBody SubmitLeaveDto submitLeaveDto, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("usession");
        try {
            SubmitLeaveValidator.validate(submitLeaveDto);
            SubmitLeaveService.submitLeave(submitLeaveDto);
            return ResponseEntity.ok("Leave application submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{leaveId}")
    public ResponseEntity<String> updateLeave(@PathVariable String leaveId, @RequestBody LeaveApplicationDTO leaveApplicationDTO)
    {
        try {
            SubmitLeaveService.updateLeave(leaveId, leaveApplicationDTO);
            return ResponseEntity.ok("Leave application updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/delete/{leaveId}")
    public ResponseEntity<String> deleteLeave(@PathVariable String leaveId) {
        try {
            SubmitLeaveService.deleteLeave(leaveId);
            return ResponseEntity.ok("Leave application deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancel/{leaveId}")
    public ResponseEntity<String> cancelLeave(@PathVariable String leaveId) {
        try {
            SubmitLeaveService.cancelLeave(leaveId);
            return ResponseEntity.ok("Leave application cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
