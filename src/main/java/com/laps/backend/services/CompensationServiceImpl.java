package com.laps.backend.services;

import com.laps.backend.models.CompensationClaim;
import com.laps.backend.repositories.CompensationClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompensationServiceImpl implements CompensationService {
    private final CompensationClaimRepository repository;


    @Autowired
    public CompensationServiceImpl(CompensationClaimRepository compensationClaimRepository) {
        this.repository = compensationClaimRepository;
    }

    @Override
    public List<CompensationClaim> findAll() {
        return repository.findAll();
    }


    @Override
    public CompensationClaim findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public CompensationClaim save(CompensationClaim compensationClaim) {
        return repository.save(compensationClaim);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
