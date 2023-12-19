package com.laps.backend.controllers;

import com.laps.backend.models.LeaveApplication;
import com.laps.backend.services.LeavehistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LeavehistoryController {
    @Autowired
    private LeavehistoryService leavehistoryService;

    @GetMapping(value = "/viewleavehistory")
    public List<LeaveApplication> getAllLeavehistorys(){
        return leavehistoryService.findAllLeavehistorys();
    }

    @GetMapping("/viewleavehistory/{status}")
    public ResponseEntity<LeaveApplication> getLeavehistoryByStatus(@PathVariable("status") String status){
        Optional<LeaveApplication> optLeavehistory = leavehistoryService.findByStatus(status);

        if(optLeavehistory.isPresent()){
            LeaveApplication leavehistory = optLeavehistory.get();
            return new
                    ResponseEntity<LeaveApplication>(leavehistory, HttpStatus.OK);
        }else{
            return new
                    ResponseEntity<LeaveApplication>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/viewleavehistory/create")
    public ResponseEntity<LeaveApplication> createLeavehistory(@RequestBody LeaveApplication inLeavehistory){
        try {
            LeaveApplication retLeavehistory = leavehistoryService.createLeavehistory(inLeavehistory);
            return new ResponseEntity<LeaveApplication>(retLeavehistory, HttpStatus.
                    CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.
                    EXPECTATION_FAILED);
        }
    }

    @PutMapping("/viewleavehistory/update/{id}")
    public ResponseEntity<LeaveApplication> updateLeavehistory(@PathVariable("id") int id, @RequestBody LeaveApplication
            inLeavehistory) {
        Optional<LeaveApplication> optLeavehistory = leavehistoryService.findById(id);
        if (optLeavehistory.isPresent()) {
// Update the managed course obj
            LeaveApplication leavehistory = optLeavehistory.get();
            leavehistory.setStartDate(inLeavehistory.getStartDate());
            leavehistory.setEndDate(inLeavehistory.getEndDate());
            leavehistory.setType(inLeavehistory.getType());
            leavehistory.setReason(inLeavehistory.getReason());
            leavehistory.setStatus(inLeavehistory.getStatus());
            leavehistory.setComment(inLeavehistory.getComment());
            LeaveApplication updatedLeavehistory = leavehistoryService.updateLeavehistory(leavehistory);
            return new ResponseEntity<LeaveApplication>(updatedLeavehistory, HttpStatus.
                    OK);
        } else {
            return new ResponseEntity<>(HttpStatus.
                    NOT_FOUND);
        }
    }

    @PutMapping("/viewleavehistory/cancel/{id}")
    public ResponseEntity<LeaveApplication> cancelLeavehistory(@PathVariable("id")int id,@RequestBody LeaveApplication inLeavehistory){
        Optional<LeaveApplication> optLeavehistory = leavehistoryService.findById(id);
        if (optLeavehistory.isPresent() && inLeavehistory.getStatus() == "Approved"){
            LeaveApplication leavehistory = optLeavehistory.get();
            leavehistory.setStartDate(inLeavehistory.getStartDate());
            leavehistory.setEndDate(inLeavehistory.getEndDate());
            leavehistory.setType(inLeavehistory.getType());
            leavehistory.setReason(inLeavehistory.getReason());
            leavehistory.setComment(inLeavehistory.getComment());
            leavehistory.setStatus("cancel");
            LeaveApplication updatedLeavehistory = leavehistoryService.cancleLeavehistory(leavehistory);
            return new ResponseEntity<LeaveApplication>(updatedLeavehistory, HttpStatus.
                    OK);
        } else {
            return new ResponseEntity<>(HttpStatus.
                    NOT_FOUND);
        }

    }

    @DeleteMapping("/viewleavehistory/delete/{id}")
    public ResponseEntity<HttpStatus> deleteLeavehistory(@PathVariable("id") int id) {
        try {
            leavehistoryService.deleteLeavehistory(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.
                    NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.
                    EXPECTATION_FAILED);
        }
    }

}
