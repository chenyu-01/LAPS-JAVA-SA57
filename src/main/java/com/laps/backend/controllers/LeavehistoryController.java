package com.laps.backend.controllers;

import com.laps.backend.models.Leavehistory;
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
    public List<Leavehistory> getAllLeavehistorys(){
        return leavehistoryService.findAllLeavehistorys();
    }

    @GetMapping("/viewleavehistory/{status}")
    public ResponseEntity<Leavehistory> getLeavehistoryByStatus(@PathVariable("status") String status){
        Optional<Leavehistory> optLeavehistory = leavehistoryService.findByStatus(status);

        if(optLeavehistory.isPresent()){
            Leavehistory leavehistory = optLeavehistory.get();
            return new
                    ResponseEntity<Leavehistory>(leavehistory, HttpStatus.OK);
        }else{
            return new
                    ResponseEntity<Leavehistory>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/viewleavehistory/create")
    public ResponseEntity<Leavehistory> createLeavehistory(@RequestBody Leavehistory inLeavehistory){
        try {
            Leavehistory retLeavehistory = leavehistoryService.createLeavehistory(inLeavehistory);
            return new ResponseEntity<Leavehistory>(retLeavehistory, HttpStatus.
                    CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.
                    EXPECTATION_FAILED);
        }
    }

    @PutMapping("/viewleavehistory/update/{id}")
    public ResponseEntity<Leavehistory> updateLeavehistory(@PathVariable("id") int id, @RequestBody Leavehistory
            inLeavehistory) {
        Optional<Leavehistory> optLeavehistory = leavehistoryService.findById(id);
        if (optLeavehistory.isPresent()) {
// Update the managed course obj
            Leavehistory leavehistory = optLeavehistory.get();
            leavehistory.setStart(inLeavehistory.getStart());
            leavehistory.setEnd(inLeavehistory.getEnd());
            leavehistory.setType(inLeavehistory.getType());
            leavehistory.setReason(inLeavehistory.getReason());
            leavehistory.setStatus(inLeavehistory.getStatus());
            Leavehistory updatedLeavehistory = leavehistoryService.updateLeavehistory(leavehistory);
            return new ResponseEntity<Leavehistory>(updatedLeavehistory, HttpStatus.
                    OK);
        } else {
            return new ResponseEntity<>(HttpStatus.
                    NOT_FOUND);
        }
    }

    @PutMapping("/viewleavehistory/cancel/{id}")
    public ResponseEntity<Leavehistory> cancelLeavehistory(@PathVariable("id")int id,@RequestBody Leavehistory inLeavehistory){
        Optional<Leavehistory> optLeavehistory = leavehistoryService.findById(id);
        if (optLeavehistory.isPresent() && inLeavehistory.getStatus() == "approved"){
            Leavehistory leavehistory = optLeavehistory.get();
            leavehistory.setStart(inLeavehistory.getStart());
            leavehistory.setEnd(inLeavehistory.getEnd());
            leavehistory.setType(inLeavehistory.getType());
            leavehistory.setReason(inLeavehistory.getReason());
            leavehistory.setStatus("cancel");
            Leavehistory updatedLeavehistory = leavehistoryService.cancleLeavehistory(leavehistory);
            return new ResponseEntity<Leavehistory>(updatedLeavehistory, HttpStatus.
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
