package com.laps.backend.services;

import com.laps.backend.models.Compensation;

import java.util.List;
import java.util.Optional;

public interface CompensationService {
    List<Compensation> findAllCompensation();
    Optional<Compensation> findById(int id);
    Optional<Compensation> findByType(String type);
    Compensation createCompensation(Compensation compensation);
    Compensation updateCompensation(Compensation compensation);
    void deleteCompensation(int id);

}
