package com.laps.backend.services;


import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import com.laps.backend.repositories.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<List<LeaveApplication>> getEmployeeAllApplications(Employee employee){
        Long id = employee.getId();
        return leaveApplicationRepository.findEmployeeAllApplications(id);
    }

    // 获取所有状态为 "Applied" 的申请
    @Override
    public List<LeaveApplication> getAllAppliedApplications() {
        return leaveApplicationRepository.findByStatus("Applied");
    }

    // 获取所有状态为 "Approved" 的申请
    @Override
    public List<LeaveApplication> getAllApprovedApplications() {
        return leaveApplicationRepository.findByStatus("Approved");
    }
    // 获取所有状态为 "Rejected" 的申请
    @Override
    public List<LeaveApplication> getAllRejectedApplications() {
        return leaveApplicationRepository.findByStatus("Rejected");
    }
    // 获取所有状态为 "Deleted" 的申请
    @Override
    public List<LeaveApplication> getAllDeletedApplications() {
        return leaveApplicationRepository.findByStatus("Deleted");
    }
    // 获取所有状态为 "Canceled" 的申请
    @Override
    public List<LeaveApplication> getAllCanceledApplications() {
        return leaveApplicationRepository.findByStatus("Canceled");
    }
    // 获取所有状态为 "Updated" 的申请
    @Override
    public List<LeaveApplication> getAllUpdatedApplications() {
        return leaveApplicationRepository.findByStatus("Updated");
    }

    @Override
    public Optional<LeaveApplication> findById(Long id,Long employee_id){
        return leaveApplicationRepository.findById(id,employee_id);
    }

    @Override
    public Optional<LeaveApplication> findById(Long id){

        return leaveApplicationRepository.findById(id);
    }


    @Override
    public List<LeaveApplication> getAppliedApplicationsByEmployee(Employee employee) {
        return leaveApplicationRepository.findByEmployee(employee);
    }

    public Optional<List<LeaveApplication>> getApprovedApplicationByEmployee(Employee employee){
        Long id = employee.getId();
        return leaveApplicationRepository.findApprovedApplicationByEmployee(id);
    }

    public Optional<List<LeaveApplication>> getRejectedApplicationByEmployee(Employee employee){
        Long id = employee.getId();
        return leaveApplicationRepository.findApprovedApplicationByEmployee(id);
    }

    public Optional<List<LeaveApplication>> getCanceledApplicationByEmployee(Employee employee){
        Long id = employee.getId();
        return leaveApplicationRepository.findApprovedApplicationByEmployee(id);
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


    @Override
    public LeaveApplication saveApplication(LeaveApplication leaveApplication){
        return leaveApplicationRepository.save(leaveApplication);
    }



}

