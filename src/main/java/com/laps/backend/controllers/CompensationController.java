package com.laps.backend.controllers;

import com.laps.backend.models.Compensation;
import com.laps.backend.services.CompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/compensation")
public class CompensationController {
    @Autowired
    private CompensationService compensationService;

    @GetMapping(value = "viewcompensation")
    public List<Compensation> getAllCompensations(){
        return compensationService.findAllCompensation();
    }

    @GetMapping(value = "/viewcompensation/{type}")
    public ResponseEntity<Compensation> getCompensationByType(@PathVariable("type") String type){
        Optional<Compensation> optCompensation = compensationService.findByType(type);

        if (optCompensation.isPresent()){
            Compensation compensation = optCompensation.get();
            return new
                    ResponseEntity<Compensation>(compensation, HttpStatus.OK);
        }else {
            return new
                    ResponseEntity<Compensation>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/viewcompensation/create")
    public ResponseEntity<Compensation> createCompensation(@RequestBody Compensation inCompensation){
        try {
            Compensation retCompensation = compensationService.createCompensation(inCompensation);
            return new ResponseEntity<Compensation>(retCompensation, HttpStatus.
                    CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.
                    EXPECTATION_FAILED);
        }
    }

    @PutMapping("/viewcompensation/update/{id}")
    public ResponseEntity<Compensation> editCompensation(@PathVariable("id") int id, @RequestBody Compensation
            inCompensation) {
        Optional<Compensation> optCompensation = compensationService.findById(id);
        if (optCompensation.isPresent()) {
// Update the managed course obj
            Compensation compensation = optCompensation.get();
            compensation.setType(inCompensation.getType());
            compensation.setDays(inCompensation.getDays());
            compensation.setStatus(inCompensation.getStatus());
            Compensation updatedCompensation = compensationService.updateCompensation(compensation);
            return new ResponseEntity<Compensation>(updatedCompensation, HttpStatus.
                    OK);
        } else {
            return new ResponseEntity<>(HttpStatus.
                    NOT_FOUND);
        }
    }

    @DeleteMapping("/viewcompensation/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCompensation(@PathVariable("id") int id) {
        try {
            compensationService.deleteCompensation(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.
                    NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.
                    EXPECTATION_FAILED);
        }
    }
}
