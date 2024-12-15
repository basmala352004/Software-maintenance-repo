package com.example.LMS.controllers;


import com.example.LMS.models.Course;
import com.example.LMS.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-course")
    public void createCourse(@RequestBody Course course) {

        courseService.createCourse(course);
    }
    @GetMapping("/display-courses")
    public ResponseEntity<List<Course>> displayCourses() {
        return ResponseEntity.ok(courseService.displayCourses());
    }

}
