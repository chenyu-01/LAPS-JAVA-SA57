package com.laps.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.laps.backend.models.PublicHolidays;
import com.laps.backend.services.PublicHolidayService;

@Controller
@RequestMapping ("/publicholidays")
public class PublicHolidayController {

    @Autowired
	private PublicHolidayService publicHolidayService;
	
	@GetMapping("/list")
	public String listPublicHolidays(Model model){
		List<PublicHolidays> publicHolidays= publicHolidayService.getAllPublicHolidays();
		model.addAttribute("publicHolidays", publicHolidays);
		return "publicholidays-list";
	}
}