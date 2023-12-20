package com.laps.backend.services;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;

import java.util.List;
import java.util.Optional;

public interface LeaveApplicationService {

    // 构造器注入

    // 获取所有状态为 "Applied" 的申请
    public List<LeaveApplication> getAllAppliedApplications() ;
    public List<LeaveApplication> getAppliedApplicationsByEmployee(Employee employee);

    void approveApplication(Long id);

    void rejectApplication(Long id);

    void addCommentToApplication(Long id, String comment);

    Optional<LeaveApplication> findById(Long id,Long employee_id);
    Optional<LeaveApplication> findById(Long id);

    Optional<List<LeaveApplication>> getEmployeeAllApplications(Employee employee);
    LeaveApplication saveApplication(LeaveApplication leaveApplication);

    List<LeaveApplication> getAllApprovedApplications();

    List<LeaveApplication> getAllRejectedApplications();


    List<LeaveApplication> getAllDeletedApplications();


    List<LeaveApplication> getAllCanceledApplications();


    List<LeaveApplication> getAllUpdatedApplications();


}
