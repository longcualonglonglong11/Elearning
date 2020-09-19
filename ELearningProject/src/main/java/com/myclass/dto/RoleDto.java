package com.myclass.dto;

import javax.validation.constraints.NotBlank;

import com.myclass.entity.Role;

public class RoleDto{
	private int id;
	@NotBlank(message = "Please enter the name")
	private String name;
	@NotBlank(message = "Please enter the description")
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
