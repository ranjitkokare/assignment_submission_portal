package com.assignmentportal.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserModel {
	@NotEmpty(message = "Name is required")
	private String name;

	@NotEmpty(message = "Email is required")
	private String email;

	
	@NotEmpty(message = "Password is required")
	private String password;
	
	private String role;
	
	private String type;
	
	private boolean twoFactorEnabled;
	
	
}
