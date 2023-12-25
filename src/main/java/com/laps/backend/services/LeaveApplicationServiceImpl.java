package com.laps.backend.services;


import com.laps.backend.models.*;
import com.laps.backend.repositories.LeaveApplicationRepository;
import com.laps.backend.repositories.UserRepository;
import com.laps.backend.specification.LeaveApplicationSpecification;
import com.laps.backend.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService{
    private final LeaveApplicationRepository leaveApplicationRepository;
    private final UserRepository userRepository;

    private final PublicHolidayService publicHolidayService;
    private final UserLeaveEntitlementService userLeaveEntitlementService;

    @Autowired
    public LeaveApplicationServiceImpl(LeaveApplicationRepository leaveApplicationRepository,
                                       UserRepository userRepository, PublicHolidayService publicHolidayService, UserLeaveEntitlementService userLeaveEntitlementService) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.userRepository = userRepository;
        this.publicHolidayService = publicHolidayService;
        this.userLeaveEntitlementService = userLeaveEntitlementService;
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

    @Override
    public Boolean approveApplication(Long id) {
        //check if leaveApplication exists
         // leaveApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        //check if leaveApplication is already approved
        Optional<LeaveApplication> leaveApplicationOpt = leaveApplicationRepository.findById(id);
        if (leaveApplicationOpt.isEmpty()) {
            throw new RuntimeException("Application not found");
        }
        LeaveApplication leaveApplication = leaveApplicationOpt.get();
        if (leaveApplication.getStatus().equals("Approved")) {
            throw new RuntimeException("Application already approved");
        }
        //check if leaveApplication is already rejected
        if (leaveApplication.getStatus().equals("Rejected")) {
            throw new RuntimeException("Application already rejected");
        }

        //approve leaveApplication
        leaveApplication.setStatus("Approved");

        return saveApplication(leaveApplication);
    }

    @Override
    public void rejectApplication(Long id) {
        //check if leaveApplication exists
        LeaveApplication leaveApplication= leaveApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        if (leaveApplication.getStatus().equals("Approved")) {
            throw new RuntimeException("Application already approved");
        }
        //check if leaveApplication is already rejected
        if (leaveApplication.getStatus().equals("Rejected")) {
            throw new RuntimeException("Application already rejected");
        }
        //reject leaveApplication
        leaveApplication.setStatus("Rejected");
        leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    public void addCommentToApplication(Long id, String comment) {
        //check if leaveApplication exists
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        //add comment to leaveApplication
        leaveApplication.setComment(comment);
        leaveApplicationRepository.save(leaveApplication);
    }

    @Override
    public List<LeaveApplication> fuzzySearchApplication(String[] keywords) {
        List<String> leaveApplication_fields = Arrays.asList("type", "reason", "contactInfo");
        List<String> user_fields = Arrays.asList("name", "email", "role");

        Specification<LeaveApplication> leaveApplication_spec = LeaveApplicationSpecification.byKeywords(leaveApplication_fields, keywords);
        Specification<User> user_spec = UserSpecification.byKeywords(user_fields, keywords);

        List<LeaveApplication> leaveApplications = leaveApplicationRepository.findAll(leaveApplication_spec);
        List<LeaveApplication> userleaveApplications = userRepository.findAll(user_spec).stream()
                .filter(Employee.class::isInstance)
                .map(Employee.class::cast)
                .flatMap(employee -> Optional.ofNullable(employee.getLeaveApplications()).stream()
                        .flatMap(Collection::stream)
                    )
                .distinct()
                .toList();

        leaveApplications = Stream.concat(leaveApplications.stream(), userleaveApplications.stream())
                .distinct()
                .toList();

        return leaveApplications;
    }
    // Methods for handling leave leaveApplications


    @Override
    public Boolean saveApplication(LeaveApplication leaveApplication){
        String status = leaveApplication.getStatus();
        if(status.equals("Canceled") || status.equals("Deleted") || status.equals("Rejected") || status.equals("Approved")){
            leaveApplicationRepository.save(leaveApplication);
            return true;
        }
        String leaveType = leaveApplication.getType();
        Employee employee = leaveApplication.getEmployee();
        long totalDays = ChronoUnit.DAYS.between(leaveApplication.getStartDate(), leaveApplication.getEndDate()) + 1;

        UserLeaveEntitlement userLeaveEntitlement = employee.getUserLeaveEntitlement();
        if (totalDays <= 14) {
            totalDays = publicHolidayService.holidayWeekendDuration(leaveApplication.getStartDate(), leaveApplication.getEndDate());
        }
        int annualEntitledDays = userLeaveEntitlement.getAnnualEntitledDays();
        int medicalEntitledDays = userLeaveEntitlement.getMedicalEntitledDays();
        float compensationEntitledDays = userLeaveEntitlement.getCompensationEntitledDays();
        switch (leaveType) {
            case "Annual":
                if (annualEntitledDays < totalDays) {
                    throw new RuntimeException("Annual leave entitlement not enough");
                }
                annualEntitledDays -= (int) totalDays;
                userLeaveEntitlement.setAnnualEntitledDays(annualEntitledDays);
                break;
            case "Medical":
                if (medicalEntitledDays < totalDays) {
                    throw new RuntimeException("Medical leave entitlement not enough");
                }
                medicalEntitledDays -= (int) totalDays;
                userLeaveEntitlement.setMedicalEntitledDays(medicalEntitledDays);
                break;
            case "Compensation":
                if (compensationEntitledDays < totalDays) {
                    throw new RuntimeException("Compensation leave entitlement not enough");
                }
                compensationEntitledDays -= totalDays;
                userLeaveEntitlement.setCompensationEntitledDays(compensationEntitledDays);
                break;
            default:
                throw new RuntimeException("Leave type not found");
        }
        boolean inContain = false;

        LocalDate startDate = leaveApplication.getStartDate();
        LocalDate endDate = leaveApplication.getEndDate();
        List<LeaveApplication> appliedApplications = getAllAppliedApplications();
        for (LeaveApplication appliedApplication : appliedApplications) {
            if (startDate.isBefore(appliedApplication.getEndDate()) || endDate.isAfter(appliedApplication.getStartDate())) {
                throw new RuntimeException("Leave application date conflict");
            }
        }
        List<LeaveApplication> updatedApplications = getAllUpdatedApplications();
        for (LeaveApplication updatedApplication : updatedApplications) {
            if (startDate.isBefore(updatedApplication.getEndDate()) || endDate.isAfter(updatedApplication.getStartDate())) {
                throw new RuntimeException("Leave application date conflict");
            }
        }
        List<LeaveApplication> approvedApplications = getAllApprovedApplications();
        for (LeaveApplication approvedApplication : approvedApplications) {
            if (startDate.isBefore(approvedApplication.getEndDate()) || endDate.isAfter(approvedApplication.getStartDate())) {
                throw new RuntimeException("Leave application date conflict");
            }
        }

        if(!inContain){
            leaveApplicationRepository.save(leaveApplication);
            userLeaveEntitlementService.save(userLeaveEntitlement);
            return true;
        }
        return false;
    }



}

