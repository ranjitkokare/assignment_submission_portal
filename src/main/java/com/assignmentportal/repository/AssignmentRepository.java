package com.assignmentportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.assignmentportal.model.Assignment;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
	List<Assignment> findByAdmin(String admin);
}
