package com.assignmentportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignmentportal.io.JwtResponse;
import com.assignmentportal.io.OtpValidationRequest;
import com.assignmentportal.model.AuthModel;
import com.assignmentportal.model.UserModel;
import com.assignmentportal.security.CustomUserDetailsService;
import com.assignmentportal.service.OtpService;
import com.assignmentportal.service.UserService;
import com.assignmentportal.util.JwtTokenUtil;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthModel authModel) throws Exception {
		
		authenticate(authModel.getEmail(), authModel.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
		
		// Check if 2FA is enabled
		if (userService.isTwoFactorEnabled(userDetails.getUsername())) {
			// Generate OTP for 2FA login
			otpService.generateAndSendOtp(userDetails.getUsername(), "2fa");
			return ResponseEntity.ok("OTP has been sent to your email. Please verify OTP to login.");
		}

		// Directly generate JWT token for non-2FA users
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserModel userModel) {
		try {
			userService.registerUser(userModel);
			return ResponseEntity.ok("OTP sent to your email. Please verify to complete registration.");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpValidationRequest otpRequest) {
		try {
			boolean isValidOtp = userService.verifyOtp(otpRequest.getUsername(), otpRequest.getOtp(),
					otpRequest.getType());

			if (isValidOtp) {
				if ("signup".equals(otpRequest.getType())) {
					return ResponseEntity.ok("User verified and registration successful.");
				} else if ("2fa".equalsIgnoreCase(otpRequest.getType())) {
					// Generate JWT after successful OTP validation for login
					final UserDetails userDetails = userDetailsService.loadUserByUsername(otpRequest.getUsername());
					final String token = jwtTokenUtil.generateToken(userDetails);
					return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
				}
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials");
		}
	}
}
