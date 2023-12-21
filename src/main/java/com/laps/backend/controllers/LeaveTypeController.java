package com.laps.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.laps.backend.exception.LeaveTypeError;
import com.laps.backend.exception.RoleNotFound;
import com.laps.backend.models.LeaveType;
import com.laps.backend.models.Role;
import com.laps.backend.services.LeaveTypeService;
import com.laps.backend.validators.LeaveTypeValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping("leavetype")
public class LeaveTypeController {

	@Autowired
	private LeaveTypeService leaveTypeService;
	
	@Autowired
	private LeaveTypeValidator leavetypeValidator;
	
	@InitBinder
	private void initLeaveTypeBinder(WebDataBinder binder) {
		binder.addValidators(leavetypeValidator);
	}
	
	@GetMapping("/list")
	public String listLeaveType(Model model) {
		List<LeaveType> leaveTypeList = leaveTypeService.getAllLeaveTypes();
		model.addAttribute("leaveTypeList", leaveTypeList);
		return "leavetype-list";
	}
	
	@GetMapping("/editDetails/{id}")
	public String editLeaveTypeDetails(@PathVariable int id, Model model) {
		Optional<LeaveType> leaveType = leaveTypeService.getLeaveTypeById(id);
		model.addAttribute("leaveType", leaveType.get());
		return "leavetype-edit";
	}
	
	@PostMapping("/editDetails/{id}")
	public String editLeaveTypeDetails(@ModelAttribute @Valid LeaveType leaveType, BindingResult bindingResult, Model model,
			@PathVariable Integer id) throws LeaveTypeError{

		// Retrieve the existing LeaveType from the database
	    LeaveType existingLeaveType = leaveTypeService.getLeaveTypeById(id).orElse(null);
	    
	    if (existingLeaveType == null) {
	    	return "redirect:/leavetype/list";
	    }
	    
	    System.out.println(leaveType.getId()+ " " + leaveType.getName() +  " " + leaveType.getRoleName() + " "+ leaveType.getEntitledNum());
	    // Update the existing LeaveType with the new data
	    existingLeaveType.setEntitledNum(leaveType.getEntitledNum());
	    System.out.println(existingLeaveType.getId()+ " " + existingLeaveType.getName() +  " " + existingLeaveType.getRoleName() + " "+ existingLeaveType.getEntitledNum());
		String message = "LeaveType was succesfully updated";
		
		//leavetypeValidator.validate(existingLeaveType, bindingResult);
        if (bindingResult.hasErrors()) {
	        System.out.println("Validation errors: " + bindingResult.getAllErrors());
	       // model.addAttribute("leaveType", existingLeaveType);
//	        model.addAttribute("BindingResult", bindingResult);
//	        model.addAttribute("errors", bindingResult);
	        return "leavetype-edit";
        }
	        
	    System.out.println(message);
	    
		leaveTypeService.changeLeaveType(existingLeaveType);
		
		return "redirect:/leavetype/list";
		
	}
	

	
}
