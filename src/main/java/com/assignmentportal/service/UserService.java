package com.assignmentportal.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.assignmentportal.model.AuthModel;
import com.assignmentportal.model.User;
import com.assignmentportal.model.UserModel;

import jakarta.validation.Valid;

public interface UserService {
	
    String login(AuthModel request);
     
    void registerUser(@Valid UserModel user);
	
	boolean verifyOtp(String username, String plainOtp, String type);

	boolean isTwoFactorEnabled(String email) throws UsernameNotFoundException;

	void completeRegistration(String username);
	
	List<String> fetchAllAdmins();
	
	//to get LoggedInUser
	User getLoggedInUser();
}
