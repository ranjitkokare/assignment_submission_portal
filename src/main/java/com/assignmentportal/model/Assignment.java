package com.assignmentportal.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "assignments")
public class Assignment {
    @Id
    private String id;
    private String userId;
    private String task;
    private String admin;
    private String status; // PENDING, ACCEPTED, REJECTED
    private LocalDateTime createdAt;
}