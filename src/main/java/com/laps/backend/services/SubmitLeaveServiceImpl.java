package com.laps.backend.services;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.LeaveApplicationDTO;
import com.laps.backend.models.SubmitLeaveDto;
import com.laps.backend.repositories.SubmitLeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubmitLeaveServiceImpl implements SubmitLeaveService {

    @Autowired
    private SubmitLeaveRepository submitLeaveRepository;

    @Autowired
    public SubmitLeaveServiceImpl(SubmitLeaveRepository submitLeaveRepository) {
        this.submitLeaveRepository = submitLeaveRepository;
    }

    @Override
    public void submitLeave(SubmitLeaveDto submitLeaveDto) throws LeaveValidationException {
        // Implement leave submission logic including validation

        validateLeaveApplication(submitLeaveDto);

        // Set the status to 'Applied'
        submitLeaveDto.setStatus("Applied");

        // Save to the repository
        LeaveApplication leaveApplication = mapDtoToEntity(submitLeaveDto);
        submitLeaveRepository.save(leaveApplication);

    }

    private void validateLeaveApplication(SubmitLeaveDto submitLeaveDto) throws LeaveValidationException {
        // Check if mandatory details are provided
        if (submitLeaveDto.getFromDate() == null || submitLeaveDto.getToDate() == null ||
                submitLeaveDto.getLeaveCategory() == null || submitLeaveDto.getLeaveCategory().isEmpty()) {
            throw new LeaveValidationException("Mandatory leave details are missing");
        }

        // Check if dates are in chronologically increasing order
        if (submitLeaveDto.getFromDate().isAfter(submitLeaveDto.getToDate())) {
            throw new LeaveValidationException("Dates From and To should be in chronological order");
        }

        // Additional validation logic as needed
        // ...

        // Check annual leave computation
        validateAnnualLeave(submitLeaveDto);
    }

    private void validateAnnualLeave(SubmitLeaveDto submitLeaveDto) throws LeaveValidationException {
        LocalDate fromDate = submitLeaveDto.getFromDate();
        LocalDate toDate = submitLeaveDto.getToDate();

        // Calculate the leave period
        long leaveDays = fromDate.until(toDate.plusDays(1)).getDays();

        // Example: Each employee is entitled to 14 days of annual leave
        int annualLeaveEntitlement = 14;

        // Additional validation based on leave type or employee designation
        // ...

        if (leaveDays > 14) {
            // Additional validation for longer leave periods
            // ...
        }
    }

    private LeaveApplication mapDtoToEntity(SubmitLeaveDto submitLeaveDto) {
        LeaveApplication leaveApplication = new LeaveApplication();

        // Map properties from DTO to Entity
        leaveApplication.setFromDate(submitLeaveDto.getFromDate());
        leaveApplication.setToDate(submitLeaveDto.getToDate());
        leaveApplication.setLeaveCategory(submitLeaveDto.getLeaveCategory());
        leaveApplication.setAdditionalReasons(submitLeaveDto.getAdditionalReasons());
        leaveApplication.setWorkDissemination(submitLeaveDto.getWorkDissemination());
        leaveApplication.setContactDetails(submitLeaveDto.getContactDetails());
        leaveApplication.setStatus(submitLeaveDto.getStatus());
        return leaveApplication;
    }
    @Override
    @Transactional
    public void updateLeave(String leaveId){

        return SubmitLeaveRepository.saveAndFlush(leaveId);
    }


    @Override
    public void deleteLeave(String leaveId) {

    }

    @Override
    public void cancelLeave(String leaveId) {

    }

    // Implement other methods (updateLeave, deleteLeave, cancelLeave) similarly
    // ...

    private LeaveApplication mapDtoToEntity(LeaveApplicationDTO leaveApplicationDTO) {
        // Convert DTO to Entity
        // ...
        return null;
    }

    @Override
    public List<LeaveApplication> getAppliedApplicationsByEmployee(Employee employee) {
        return submitLeaveRepository.findByEmployee(employee);
    }

    public class LeaveValidationException extends Exception {

        public LeaveValidationException(String message) {
            super(message);
        }

        public class LeaveNotFoundException extends Exception {

            public LeaveNotFoundException(String message) {
                super(message);
            }
}
