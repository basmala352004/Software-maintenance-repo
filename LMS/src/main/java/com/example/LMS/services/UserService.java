package com.example.LMS.services;

import com.example.LMS.models.Profile;
import com.example.LMS.models.User;
import com.example.LMS.models.loginData;
import com.example.LMS.repositories.UserRepository;
import com.example.LMS.repositories.profileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private profileRepository profileRepository;


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {

        // validate
        if (user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Name is required.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password is required.");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Role is required.");
        }
        if (user.getId() != null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("you cant assign id , its auto generated ID.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("mail is required.");
        }
        if (!user.getEmail().contains("@gmail.com")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("mail must include @gmail.com.");
        }
        Optional<User> existingUser = userRepository.findByNameAndEmail(user.getName(), user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with the same name and  email already exists.");
        }


        User savedUser = userRepository.save(user);

        Profile profile = new Profile(savedUser);
        profileRepository.save(profile);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody loginData loginData) {

        Optional<User> user = userRepository.findByNameAndEmail(loginData.getUsername(), loginData.getEmail());

        if (user.isPresent()) {

            if (user.get().getPassword().equals(loginData.getPassword())) {
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Incorrect password! Please try again.");
            }
        }


        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("check that username correct or email or  register first .");
    }




    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no users.");
        }
        return ResponseEntity.ok(users);
    }



    @PostMapping("/editprofile")
    public ResponseEntity<Object> editProfile(@RequestBody Profile userProfile) {
        // Retrieve the existing profile by user ID
        Profile existingProfile = profileRepository.getProfileById(userProfile.getUser().getId());

        if (existingProfile != null) {
            User user = existingProfile.getUser();

            boolean isNameChanged = !user.getName().equals(userProfile.getUser().getName());
            boolean isEmailChanged = !user.getEmail().equals(userProfile.getUser().getEmail());

            if (isNameChanged || isEmailChanged) {
                Optional<User> existingUser = userRepository.findByNameAndEmail(userProfile.getUser().getName(), userProfile.getUser().getEmail());

                if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("The name '" + userProfile.getUser().getName() + "' and email '" + userProfile.getUser().getEmail() + "' are already in use by another user. Please choose a different name or email.");
                }

                user.setName(userProfile.getUser().getName());
                user.setEmail(userProfile.getUser().getEmail());
            }


            existingProfile.setAddress(userProfile.getAddress());
            existingProfile.setPhoneNumber(userProfile.getPhoneNumber());
            user.setRole(userProfile.getUser().getRole());
            user.setPassword(userProfile.getUser().getPassword());
            userRepository.save(user);
            profileRepository.save(existingProfile);

            return ResponseEntity.ok(existingProfile);
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Profile not found for user ID: " + userProfile.getUser().getId());
        }
    }

//

    @GetMapping("/profiles/{userId}")
    public ResponseEntity<Object> ViewProfile(@PathVariable Integer userId) {

        Profile profile = profileRepository.getProfileById(userId);


        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Profile not found for user ID: " + userId);
        }
    }
}