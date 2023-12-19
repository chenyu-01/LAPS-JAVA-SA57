package com.laps.backend.services;

import com.laps.backend.models.Compensation;
import com.laps.backend.repositories.CompensationReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompensationServiceImpl implements CompensationService{
    @Autowired
    private CompensationReposity compensationReposity;

    @Override
    public List<Compensation> findAllCompensation(){
        return compensationReposity.findAll();
    }

    @Override
    public Optional<Compensation> findById(int id){
        return compensationReposity.findById(id);
    }
    @Override
    public Optional<Compensation> findByType(String type){
        return compensationReposity.findByType(type);
    }

    @Override
    public Compensation createCompensation(Compensation compensation){
        return compensationReposity.save(compensation);
    }
    @Override
    public Compensation updateCompensation(Compensation compensation){
        return compensationReposity.save(compensation);
    }
    @Override
    public void deleteCompensation(int id){
        compensationReposity.deleteById(id);
    }

}
