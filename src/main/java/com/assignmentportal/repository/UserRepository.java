package com.assignmentportal.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.assignmentportal.model.User;

public interface UserRepository extends MongoRepository<User, String> {
  //check email exists or not
  	Boolean existsByEmail(String email);
  	
  	Optional<User> findByEmail(String email);
}
