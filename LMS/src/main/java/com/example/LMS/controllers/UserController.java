package com.example.LMS.controllers;

import com.example.LMS.models.Profile;
import com.example.LMS.models.User;
import com.example.LMS.models.loginData;
import com.example.LMS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        return userService.register(user);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody loginData loginData) {
        return userService.login(loginData);
    }


    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/editprofile")
    public ResponseEntity<Object> editProfile(@RequestBody Profile userProfile) {
        return userService.editProfile(userProfile);
    }


    @GetMapping("/showprofile/{userId}")
    public ResponseEntity<Object> viewProfile(@PathVariable Integer userId) {
        return userService.ViewProfile(userId);
    }
}
