package com.laps.backend.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable {
	private static final long serialversionUID = 6529685098267757690L;
	
	@Id
	@Column(name = "roleid")
	private int roleId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	public Role() {}
	
	public Role(String name,String description) {
		this.name = name;
		this.description = description;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
