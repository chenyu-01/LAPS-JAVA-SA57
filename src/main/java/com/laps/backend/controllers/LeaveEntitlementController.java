package com.laps.backend.controllers;

import com.laps.backend.models.LeaveEntitlementDTO;
import com.laps.backend.models.UserLeaveEntitlement;
import com.laps.backend.services.UserLeaveEntitlementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leave-entitlement")
public class LeaveEntitlementController {

    private final UserLeaveEntitlementServiceImpl userLeaveEntitlementService;
    @Autowired
    public LeaveEntitlementController(UserLeaveEntitlementServiceImpl userLeaveEntitlementService) {
        this.userLeaveEntitlementService = userLeaveEntitlementService;
    }



    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getLeaveEntitlement(@PathVariable Long userId){
        UserLeaveEntitlement userLeaveEntitlement = userLeaveEntitlementService.findByUserId(userId);
        if (userLeaveEntitlement != null) {
            LeaveEntitlementDTO leaveEntitlementDTO = new LeaveEntitlementDTO(userLeaveEntitlement);
            return ResponseEntity.ok(leaveEntitlementDTO);
        }
        return ResponseEntity.badRequest().body("User leave entitlement not found");
    }


}
