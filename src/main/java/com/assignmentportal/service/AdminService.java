package com.assignmentportal.service;

import java.util.List;

import com.assignmentportal.dto.AdminAssignmentResponse;
import com.assignmentportal.model.Assignment;

public interface AdminService {
	List<AdminAssignmentResponse> getAssignments(String admin);
    void acceptAssignment(String assignmentId);
    void rejectAssignment(String assignmentId);
}
