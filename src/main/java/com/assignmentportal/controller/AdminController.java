package com.assignmentportal.controller;


import com.assignmentportal.dto.AdminAssignmentResponse;
import com.assignmentportal.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    
    @GetMapping("/assignments")
    public ResponseEntity<List<AdminAssignmentResponse>> viewAssignments(@RequestParam String adminName) {
        List<AdminAssignmentResponse> assignments = adminService.getAssignments(adminName);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping("/assignments/{id}/accept")
    public ResponseEntity<String> acceptAssignment(@PathVariable String id) {
        adminService.acceptAssignment(id);
        return ResponseEntity.ok("Assignment accepted successfully.");
    }

    @PostMapping("/assignments/{id}/reject")
    public ResponseEntity<String> rejectAssignment(@PathVariable String id) {
        adminService.rejectAssignment(id);
        return ResponseEntity.ok("Assignment rejected successfully.");
    }
}
