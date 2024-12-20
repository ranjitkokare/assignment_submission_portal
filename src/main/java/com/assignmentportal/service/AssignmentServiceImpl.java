package com.assignmentportal.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.assignmentportal.io.AssignmentRequest;
import com.assignmentportal.model.Assignment;
import com.assignmentportal.repository.AssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

	private final AssignmentRepository assignmentRepository;
	
    @Override
    public void uploadAssignment(AssignmentRequest request) {
        Assignment assignment = Assignment.builder()
                .name(request.getName())
                .task(request.getTask())
                .admin(request.getAdmin())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        assignmentRepository.save(assignment);
    }

}
