package com.laps.backend.models;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private LeaveType leavetype;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private LeaveStatus status;
    

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LeaveType getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(LeaveType leavetype) {
		this.leavetype = leavetype;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public LeaveStatus getStatus() {
		return status;
	}
	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	
	public int getMaxDays() {
		return maxDays;
	}
	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}
	// mandatory for name, maxDays. and name should be unique
    @Column(nullable = false, unique = true)
    private String name; // e.g., Annual, Medical, Compensation, etc.
    @Column(nullable = false)
    private int maxDays; // Max days allowed for this leave type

    // Standard getters and setters
}
