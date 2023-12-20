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

    private CompensationController(CompensationService compensationService){
        this.compensationService = compensationService;
    }
}
