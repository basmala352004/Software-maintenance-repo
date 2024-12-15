package com.example.LMS.controllers;

import com.example.LMS.models.Course;
import com.example.LMS.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private static final String UPLOAD_DIRECTORY = "uploads/";

    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        if (course.getListLessons() == null) {
            course.setListLessons(new ArrayList<>());
        }
        if (course.getMediaFiles() == null) {
            course.setMediaFiles(new ArrayList<>());
        }
        courseService.createCourse(course);
        return ResponseEntity.ok("Course created successfully");
    }

    @PostMapping("/{courseId}/upload-media")
    public ResponseEntity<String> uploadMedia(@PathVariable String courseId, @RequestParam("file") MultipartFile file) {
        try {
            // store the uploaded files
            File uploadDir = new File("C:/uploads/");
            if (!uploadDir.exists()) {
                // Create the directory if it doesn't exist
                uploadDir.mkdir();
            }

            // Save the file to the server's upload directory
            String filePath = "C:/uploads/" + file.getOriginalFilename();  // Set a full file path
            file.transferTo(new File(filePath));

            // Add the file path to the course
            courseService.addMediaFile(courseId, filePath);

            return ResponseEntity.ok("Media file uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }


    @GetMapping("/display")
    public ResponseEntity<List<Course>> displayCourses() {
        List<Course> courses = courseService.displayCourses();
        return ResponseEntity.ok(courses);
    }
}

