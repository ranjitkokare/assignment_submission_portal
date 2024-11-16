package com.assignmentportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignmentportal.dto.LoginRequest;
import com.assignmentportal.dto.UserRequest;
import com.assignmentportal.exception.UnauthorizedException;
import com.assignmentportal.model.User;
import com.assignmentportal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	 private final UserRepository userRepo;

	    @Override
	    public void register(UserRequest request) {
	        User user = User.builder()
	        		.name(request.getName())
	                .username(request.getUsername())
	                .password(request.getPassword()) // Use BCrypt in production
	                .role("USER")
	                .build();
	        userRepo.save(user);
	    }

	    @Override
	    public String login(LoginRequest request) {
	        User user = userRepo.findByUsername(request.getUsername())
	                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
	        // Compare passwords (use BCrypt in production)
	        if (!user.getPassword().equals(request.getPassword())) {
	            throw new UnauthorizedException("Invalid credentials");
	        }
	        return "Login successful";
	    }

	    @Override
	    public List<String> fetchAllAdmins() {
	        return userRepo.findAll()
	                .stream()
	                .filter(user -> user.getRole().equals("ADMIN"))
	                .map(User::getUsername)
	                .toList();
	    }

}
