package com.example.LMS.services;

import com.example.LMS.models.AuthentictationResponse;
import com.example.LMS.models.StudentModel;
import com.example.LMS.models.User;
import com.example.LMS.repositories.StudentRepository;
import com.example.LMS.repositories.UserRepository;
import com.example.LMS.SpringSecurity.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final jwtService jwtService;

    // Register a new user
    public AuthentictationResponse register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole(User.ROLE_STUDENT);  // Default to STUDENT role
        }
        // Encrypt the password and save the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(Objects.equals(user.getRole(), "ROLE_STUDENT")){
            StudentModel student = new StudentModel(user.getName(),  user.getRole(), user.getPassword(),user.getEmail());
            studentRepository.save(student);
        }
        else
            userRepository.save(user);

        // Generate JWT token after saving the user
        String jwtToken = jwtService.generateToken(user);
        return new AuthentictationResponse(jwtToken);
    }

    // Login and generate JWT token
    public AuthentictationResponse login(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isEmpty() || !passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String jwtToken = jwtService.generateToken(existingUser.get());
        return new AuthentictationResponse(jwtToken);
    }


    public ResponseEntity<Object> getAllUsers() {
        // Fetching all users from the database
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.status(404).body("No users found");
        }

        return ResponseEntity.ok(users);
    }

}
