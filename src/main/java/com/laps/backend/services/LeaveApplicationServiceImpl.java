package com.laps.backend.services;

import com.laps.backend.models.LeaveApplication;
import com.laps.backend.repositories.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService{
    @Autowired
    private final LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    public LeaveApplicationServiceImpl(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public List<LeaveApplication> getAllApplications() {
        return leaveApplicationRepository.findAll();
    }


    // 获取所有状态为 "Applied" 的申请
    public List<LeaveApplication> getAllAppliedApplications() {
        return leaveApplicationRepository.findByStatus("Applied");
    }

    @Override
    public void approveApplication(Long id) {
        //check if application exists
        leaveApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        //check if application is already approved
        LeaveApplication application = leaveApplicationRepository.findById(id).get();
        if (application.getStatus().equals("Approved")) {
            throw new RuntimeException("Application already approved");
        }
        //check if application is already rejected
        if (application.getStatus().equals("Rejected")) {
            throw new RuntimeException("Application already rejected");
        }
        //approve application
        application.setStatus("Approved");
        leaveApplicationRepository.save(application);
    }

    @Override
    public void rejectApplication(Long id) {
        //check if application exists
        leaveApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        //check if application is already approved
        LeaveApplication application = leaveApplicationRepository.findById(id).get();
        if (application.getStatus().equals("Approved")) {
            throw new RuntimeException("Application already approved");
        }
        //check if application is already rejected
        if (application.getStatus().equals("Rejected")) {
            throw new RuntimeException("Application already rejected");
        }
        //reject application
        application.setStatus("Rejected");
        leaveApplicationRepository.save(application);
    }

    @Override
    public void addCommentToApplication(Long id, String comment) {
        //check if application exists
        leaveApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        //add comment to application
        LeaveApplication application = leaveApplicationRepository.findById(id).get();
        application.setComment(comment);
        leaveApplicationRepository.save(application);
    }
    // Methods for handling leave applications
}

