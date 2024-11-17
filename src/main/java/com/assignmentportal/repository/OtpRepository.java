package com.assignmentportal.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.assignmentportal.model.Otp;

public interface OtpRepository extends MongoRepository<Otp, String> {

	// Fetch the most recent OTP by username and type
		Optional<Otp> findTopByUsernameAndTypeOrderByIdDesc(String username, String type);
}
