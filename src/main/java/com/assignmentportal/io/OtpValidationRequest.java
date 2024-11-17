package com.assignmentportal.io;

import lombok.Data;

@Data
public class OtpValidationRequest {

	private String username;
	
	private String otp;
	
	private String type;
	
	
}
