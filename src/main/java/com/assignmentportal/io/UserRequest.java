package com.assignmentportal.io;

import lombok.Data;

@Data
public class UserRequest {
	private String name;
    private String username;
    private String password;
    private String role;
}
