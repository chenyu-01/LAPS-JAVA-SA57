package com.laps.backend.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.laps.backend.models.LeaveApplication;

@Component
public class LeaveApplicationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return LeaveApplication.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        LeaveApplication leaveApplication = (LeaveApplication) object;

        // Validate startDate and endDate
        if (leaveApplication.getStartDate() != null && leaveApplication.getEndDate() != null) {
            if (!leaveApplication.getStartDate().isBefore(leaveApplication.getEndDate())) {
                errors.rejectValue("endDate", "error.endDate", "End date must be after start date");
            }
        } else {
            errors.rejectValue("startDate", "error.startDate", "Start date is required");
            errors.rejectValue("endDate", "error.endDate", "End date is required");
        }

        // if overseas leave, must have a valid contact Info (email and phone)
        if (leaveApplication.getIsOverseas()) {
            if (leaveApplication.getContactInfo() == null || leaveApplication.getContactInfo().isEmpty()) {
                errors.rejectValue("contactInfo", "error.contactInfo", "Contact info is required for overseas leave");
            }
        }
    }
}
