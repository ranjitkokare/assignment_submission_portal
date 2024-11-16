package com.assignmentportal.service;

import java.util.List;

import com.assignmentportal.dto.LoginRequest;
import com.assignmentportal.dto.UserRequest;

public interface UserService {
    void register(UserRequest request);
    String login(LoginRequest request);
    List<String> fetchAllAdmins();
}
