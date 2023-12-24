package com.laps.backend.models;

import java.io.Serializable;
import java.util.Objects;

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
	private String roleId;
	
	@Column(name = "name")
	private String name;

	
	public Role() {}
	
  public Role(String roleId) {
	    this.roleId = roleId;
	  }
	
	public Role(String roleId, String name) {
		this.roleId = roleId;
		this.name = name;

	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
