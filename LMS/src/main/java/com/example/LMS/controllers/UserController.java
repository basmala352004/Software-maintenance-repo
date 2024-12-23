package com.example.LMS.controllers;

import com.example.LMS.models.AuthentictationResponse;
import com.example.LMS.models.User;
import com.example.LMS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user with role-based access
    @PostMapping("/register")
    public ResponseEntity<AuthentictationResponse> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user)); // Register method in service
    }

    // Login and get JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthentictationResponse> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user)); // Login method in service
    }
}
