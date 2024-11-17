package com.assignmentportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignmentportal.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
    private final UserService userService;

    @GetMapping("/admins")
    public ResponseEntity<List<String>> fetchAllAdmins() {
        List<String> admins = userService.fetchAllAdmins();
        return ResponseEntity.ok(admins);
    }
}
