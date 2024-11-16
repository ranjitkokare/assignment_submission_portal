package com.assignmentportal.dto;

import lombok.Data;

@Data
public class AssignmentRequest {
    private String userId;
    private String task;
    private String admin;
}
