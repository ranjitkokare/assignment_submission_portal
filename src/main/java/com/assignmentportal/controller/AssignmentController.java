package com.assignmentportal.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignmentportal.io.AssignmentRequest;
import com.assignmentportal.service.AssignmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {
	private final AssignmentService assignmentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAssignment(@RequestBody AssignmentRequest request) {
        assignmentService.uploadAssignment(request);
        return ResponseEntity.ok("Assignment uploaded successfully.");
    }
}
    
    
