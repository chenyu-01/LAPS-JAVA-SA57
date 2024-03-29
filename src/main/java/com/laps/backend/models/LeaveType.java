package com.laps.backend.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Getter
@Setter
@Table(name = "leaveType")
	public class LeaveType{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	private String roleName;
	
    @Column(name = "name", columnDefinition = "ENUM('ANNUAL', 'MEDICAL', 'COMPENSATION')")
    @Enumerated(EnumType.STRING)
    private LeaveTypeEnum name;
//    @Positive(message = "It must be a positive number")
    private int entitledNum;
    
    public LeaveType() {}

	public LeaveType(String roleName, LeaveTypeEnum name, int entitledNum) {
		super();
		this.roleName = roleName;
		this.name = name;
		this.entitledNum = entitledNum;
	}


}
