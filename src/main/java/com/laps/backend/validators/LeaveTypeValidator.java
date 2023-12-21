package com.laps.backend.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.laps.backend.models.LeaveType;
import com.laps.backend.models.LeaveTypeEnum;

@Component
public class LeaveTypeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
	    return LeaveType.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entitledNum", "error.entitledNum", "error.leaveType.entitledNum.empty");

		System.out.println("Validator being called");

		LeaveType leavetype = (LeaveType) object;
		
		if (leavetype.getEntitledNum() < 1) {
			errors.rejectValue("entitledNum", "error.entitledNum", "Medical leave cannot be more than 60");
		}

		if ((LeaveTypeEnum)leavetype.getName() == LeaveTypeEnum.MEDICAL && leavetype.getEntitledNum() > 60) {
			errors.rejectValue("entitledNum", "error.entitledNum", "Medical leave cannot be more than 60");
		} 
		
		if ((LeaveTypeEnum)leavetype.getName() == LeaveTypeEnum.ANNUAL && leavetype.getRoleName().equalsIgnoreCase("Admin")
				&& leavetype.getEntitledNum() < 14) {
			errors.rejectValue("entitledNum", "error.entitledNum", "Admin is entitled for a minimum of 14 day annual leave");
		}

		if ((LeaveTypeEnum)leavetype.getName() == LeaveTypeEnum.ANNUAL && !leavetype.getRoleName().equalsIgnoreCase("Admin")
				&& leavetype.getEntitledNum() < 18) {
			errors.rejectValue("entitledNum", "error.entitledNum", "Employee is entitled for a minimum of 18 day annual leave");

		}

		if (leavetype.getEntitledNum() < 1) {
			errors.rejectValue("entitledNum", "error.entitledNum", "EntitledNum should be greater than 0");
		}
	}
}
