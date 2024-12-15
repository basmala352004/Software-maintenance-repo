package com.example.LMS.controllers;

import com.example.LMS.models.Course;
import com.example.LMS.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        courseService.createCourse(course);
        return ResponseEntity.ok("Course created successfully");
    }

    @GetMapping("/display")
    public ResponseEntity<List<Course>> displayCourses() {
        List<Course> courses = courseService.displayCourses();
        return ResponseEntity.ok(courses);
    }
}
