package com.assignmentportal.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document(collection = "otp")
public class Otp {

	 	@Id
	    private String id;

	    private String username;

	    private String otp;

	    private String type;

	    private boolean status;  // false = not verified, true = verified
	    
	    private LocalDateTime expirationTime;
}
