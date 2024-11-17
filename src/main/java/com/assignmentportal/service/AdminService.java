package com.assignmentportal.service;

import java.util.List;

import com.assignmentportal.io.AdminAssignmentResponse;

public interface AdminService {
	List<AdminAssignmentResponse> getAssignments(String admin);
	
    void acceptAssignment(String assignmentId);
    
    void rejectAssignment(String assignmentId);
}
