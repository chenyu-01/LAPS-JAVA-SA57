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

import com.laps.backend.exception.RoleNotFound;
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
		model.addAttribute("roleList",role);
		
		return "role-list";
	}
	
	@GetMapping("/create")
	public String newRole(Model model) {
		model.addAttribute("role", new Role());
		
		return "role-new";
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
	

	@GetMapping("edit/{id}")
	public String editRole(@PathVariable String id,Model model) {
		
		Optional<Role> role = roleService.findRole(id);
		model.addAttribute("role", role.get());
		
		return "role-edit";
	}
	
	@PostMapping("edit/{id}")
	public String editRole(@ModelAttribute @Valid Role role, BindingResult bindingResult,
			@PathVariable String id) throws RoleNotFound{
		if (bindingResult.hasErrors()) {
			return "role-edit";
		}
		
		String message = "Role was succesfully updated";
		System.out.println(message);
		roleService.changeRole(role);
		
		return "redirect:/admin/role/list";
		
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable String id) throws RoleNotFound{
		Optional<Role> role = roleService.findRole(id);
		roleService.removeRole(role);
		
		String message = "The role was successfully deleted";
		System.out.println(message);
		
		return "redirect:/admin/role/list";
		
		
	}
	
}
