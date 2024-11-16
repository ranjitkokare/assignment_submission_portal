package com.assignmentportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignmentportal.dto.LoginRequest;
import com.assignmentportal.dto.UserRequest;
import com.assignmentportal.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        String message = userService.login(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<String>> fetchAllAdmins() {
        List<String> admins = userService.fetchAllAdmins();
        return ResponseEntity.ok(admins);
    }
}
