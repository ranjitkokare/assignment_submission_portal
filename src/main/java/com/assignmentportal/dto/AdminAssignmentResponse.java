package com.assignmentportal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminAssignmentResponse {
    private String userId;
    private String task;
    private String status;
    private LocalDateTime createdAt;
}
