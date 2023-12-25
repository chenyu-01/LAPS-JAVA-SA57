package com.laps.backend.controllers;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/create")
	public String createPublicHolidays(Model model){
		model.addAttribute("publicHolidays",new PublicHolidays());
		return "createPublicHolidays";
	}

	@PostMapping("/create")
	public String createPublicHolidays(@ModelAttribute @Valid PublicHolidays publicHoliday, BindingResult result,Model model){
		if(result.hasErrors()){
			return "createPublicHolidays";
		}
		publicHolidayService.createpublicHolidays(publicHoliday);

		return "redirect:/publicholidays/list";
	}



	@GetMapping("/edit/{id}")
	public String updatePublicHoliday(@PathVariable long id,Model model){
		Optional<PublicHolidays> publicHoliday = publicHolidayService.findPublicHolidaysById(id);
		if (publicHoliday.isEmpty()) {
			return "redirect:/publicholidays/list";
		}
		model.addAttribute("publicHoliday", publicHoliday.get());
//		model.addAttribute("publicHoliday",publicHolidayService.findPublicHolidaysById(id));
		return "publicholidays-edit";
	}
	@PostMapping("/edit/{id}")
	public String updatePublicHoliday(@PathVariable long id, @ModelAttribute PublicHolidays publicHolidays,
									  BindingResult result){
		 if (result.hasErrors()){
			 return "publicholidays-edit";
		 }


		 publicHolidayService.changePublicHoliday(publicHolidays);

		 return "redirect:/publicholidays/list";

	}

	@GetMapping("/delete/{id}")
	public String deletePublicHoliday(@PathVariable long id){
//		Optional<PublicHolidays> publicHolidays = publicHolidayService.findPublicHolidaysById(id);
		publicHolidayService.deletePublicHolidayById(id);
		return "redirect:/publicholidays/list";
	}

}