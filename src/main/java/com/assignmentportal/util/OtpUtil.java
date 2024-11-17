package com.assignmentportal.util;

import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class OtpUtil {

	 // Generate random OTP
    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Hash OTP
    public static String hashOtp(String otp) {
        return BCrypt.hashpw(otp, BCrypt.gensalt(10));
    }

    // Validate OTP
    public static boolean validateOtp(String otp, String hashedOtp) {
        return BCrypt.checkpw(otp, hashedOtp);
    }
}