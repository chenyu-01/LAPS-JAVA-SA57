package com.laps.backend.models;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PublicHolidays {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate date;
	
	public PublicHolidays() {};
	
	
	public PublicHolidays(LocalDate date, String name) {
		super();
		this.name = name;
		this.date = date;
	}

	
}
