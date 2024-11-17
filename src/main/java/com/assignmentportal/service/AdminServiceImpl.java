package com.assignmentportal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.assignmentportal.exception.ResourceNotFoundException;
import com.assignmentportal.io.AdminAssignmentResponse;
import com.assignmentportal.model.Assignment;
import com.assignmentportal.repository.AssignmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	
    private final AssignmentRepository assignmentRepo;

    @Override
    public List<AdminAssignmentResponse> getAssignments(String adminName) {
        List<Assignment> assignments = assignmentRepo.findByAdmin(adminName);

        if (assignments.isEmpty()) {
            throw new ResourceNotFoundException("No assignments found for admin: " + adminName);
        }

        return assignments.stream()
                .map(assignment -> new AdminAssignmentResponse(
                        assignment.getName(),
                        assignment.getTask(),
                        assignment.getStatus(),
                        assignment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void acceptAssignment(String assignmentId) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with ID: " + assignmentId));

        assignment.setStatus("ACCEPTED");
        assignmentRepo.save(assignment);
    }

    @Override
    public void rejectAssignment(String assignmentId) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with ID: " + assignmentId));

        assignment.setStatus("REJECTED");
        assignmentRepo.save(assignment);
    }
}
