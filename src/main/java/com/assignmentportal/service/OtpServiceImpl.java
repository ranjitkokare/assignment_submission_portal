package com.assignmentportal.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignmentportal.model.Otp;
import com.assignmentportal.repository.OtpRepository;
import com.assignmentportal.util.OtpUtil;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void generateAndSendOtp(String username, String type) {
        String otp = OtpUtil.generateOtp();
        String hashedOtp = OtpUtil.hashOtp(otp);

        Otp otpEntity = new Otp();
        otpEntity.setUsername(username);
        otpEntity.setOtp(hashedOtp);
        otpEntity.setType(type);
        otpEntity.setStatus(false);
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);

        // Send OTP via email
        emailService.sendOtpEmail(username, otp);
    }

}