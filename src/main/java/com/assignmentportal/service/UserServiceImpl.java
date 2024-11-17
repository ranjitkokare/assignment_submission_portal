package com.assignmentportal.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignmentportal.exception.ItemAlreadyExistsException;
import com.assignmentportal.exception.UnauthorizedException;
import com.assignmentportal.model.AuthModel;
import com.assignmentportal.model.Otp;
import com.assignmentportal.model.User;
import com.assignmentportal.model.UserModel;
import com.assignmentportal.repository.OtpRepository;
import com.assignmentportal.repository.UserRepository;
import com.assignmentportal.util.OtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	 private final UserRepository userRepo;

	 	@Autowired
	    private PasswordEncoder bcryptEncoder;
	    
	    private final OtpRepository otpRepository;
	    
	    private final EmailService emailService;

		private final Map<String, User> temporaryUserStorage = new HashMap<>();

	    @Override
	    public void registerUser(UserModel user) {
	    	// Check if the email already exists
	        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
	        if (existingUser.isPresent()) {
	            throw new ItemAlreadyExistsException("User already exists with email: " + user.getEmail());
	        }

	        User newUser = new User();
			BeanUtils.copyProperties(user, newUser);// src Obj , dest obj
			
			// Encrypt password (store in memory, not DB yet)
			newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));

			String otp = generateAndStoreOtp(user.getEmail(), "signup", newUser);
	        emailService.sendOtpEmail(user.getEmail(), otp);
	    }

	    @Override
	    public void completeRegistration(String otp) {
	        // Fetch temporary user data and store in database after OTP verification
	        User newUser = temporaryUserStorage.get(otp);
	        if (newUser != null) {
	            newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
	            userRepo.save(newUser);
	            temporaryUserStorage.remove(otp);
	        } else {
	            throw new RuntimeException("User data not found for username: ");
	        }
	    }
	    
	    private String generateAndStoreOtp(String username, String type, User newUser) {
	        String otp = OtpUtil.generateOtp();
	        String hashedOtp = OtpUtil.hashOtp(otp);

	        Otp otpEntity = new Otp();
	        otpEntity.setUsername(username);
	        otpEntity.setOtp(hashedOtp);
	        otpEntity.setType(type);
	        otpEntity.setStatus(false);
	        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(10));
	        otpRepository.save(otpEntity);

	        temporaryUserStorage.put(hashedOtp, newUser);
	        return otp;
	    }

	    @Override
	    public boolean verifyOtp(String username, String plainOtp, String type) {
	        Optional<Otp> otpOptional = otpRepository.findTopByUsernameAndTypeOrderByIdDesc(username, type);

	        if (otpOptional.isPresent()) {
	            Otp otpEntity = otpOptional.get();
	            if (isValidOtp(otpEntity, plainOtp)) {
	                if ("signup".equals(type)) {
	                    User newUser = temporaryUserStorage.remove(otpEntity.getOtp());
	                    if (newUser == null) {
	                        throw new RuntimeException("User details not found for the provided OTP.");
	                    }
	                    userRepo.save(newUser);
	                }

	                otpEntity.setStatus(true);
	                otpRepository.save(otpEntity);
	                return true;
	            }
	        }
	        throw new RuntimeException("Invalid or expired OTP.");
	    }
	    
	    @Override
	    public boolean isTwoFactorEnabled(String email) {
	        User user = userRepo.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));
	        return user.isTwoFactorEnabled();
	    }
	    
	    private boolean isValidOtp(Otp otpEntity, String plainOtp) {
	        return otpEntity.getExpirationTime().isAfter(LocalDateTime.now()) && 
	               OtpUtil.validateOtp(plainOtp, otpEntity.getOtp());
	    }

	    @Override
	    public String login(AuthModel request) {
	        User user = userRepo.findByEmail(request.getEmail())
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
	                .map(User::getName)
	                .toList();
	    }
	    
	    @Override
		public User getLoggedInUser() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			String email = authentication.getName();
			
			return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email: "+email));
		}

}
