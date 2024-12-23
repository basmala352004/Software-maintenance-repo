package com.example.LMS.controllers;

import com.example.LMS.models.CourseModel;
import com.example.LMS.services.CourseService;
import com.example.LMS.services.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;




@RestController
@RequestMapping("/instructor")
public class InstructorController

{
    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping("/createCourse")
    public ResponseEntity<String> createCourse(@RequestBody CourseModel course) {
        if (course.getListLessons() == null) {
            course.setListLessons(new ArrayList<>());
        }
        if (course.getMediaFiles() == null) {
            course.setMediaFiles(new ArrayList<>());
        }

        courseService.createCourse(course);  // Create the course using the service
        return ResponseEntity.ok("Course created successfully");
    }



}
