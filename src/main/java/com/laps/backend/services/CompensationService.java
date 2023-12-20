package com.laps.backend.services;

import com.laps.backend.models.CompensationClaim;

import java.util.List;

public interface CompensationService {

    List<CompensationClaim> findAll();

    CompensationClaim findById(Long id);

    CompensationClaim save(CompensationClaim compensationClaim);

    void deleteById(Long id);
}
