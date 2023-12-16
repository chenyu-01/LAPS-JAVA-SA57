package com.laps.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.laps.backend.models.Role;
import com.laps.backend.services.RoleService;
import com.laps.backend.validators.RoleValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/role/")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleValidator rolevalidator;
	
	@InitBinder("role")
	private void initRoleBinder(WebDataBinder binder) {
		binder.addValidators(rolevalidator);
	}
	
	@GetMapping("/list")
	public String rolelist(Model model) {
		List <Role> role = roleService.findAllRoles();
		model.addAttribute("role",role);
		
		return "role-list";
	}
	
	@GetMapping("/create")
	public String newRole(Model model) {
		model.addAttribute("role", new Role());
		
		return "roll-new";
	}
	
	@PostMapping("/create")
	public String createRole(@ModelAttribute @Valid Role role,BindingResult bindingresult) {
		if(bindingresult.hasErrors()) {
			return "role-new";
		}
		
		String message = "New role successfully created";
		System.out.println(message);
		roleService.createRole(role);
		
		return "redirect:/admin/role/list";
		
	}
	
}
