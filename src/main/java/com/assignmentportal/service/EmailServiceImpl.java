package com.assignmentportal.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender mailSender;
	
	public void sendOtpEmail(String to, String otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Thank you for joining us.");
		message.setText("Please use the OTP below for Email Verification.\n"
		        + "Your OTP is: " + otp + "\n"
		        + "OTP will expire in 10 minutes.");
		mailSender.send(message);
    }

}
