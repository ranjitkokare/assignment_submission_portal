package com.assignmentportal.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObject {
    private int statusCode;
    private String message;
    private Date timestamp;
}