package com.example.LMS.controllers;

import com.example.LMS.models.CourseModel;
import com.example.LMS.models.LessonModel;

import com.example.LMS.services.CourseService;
import com.example.LMS.services.LessonService;
import com.example.LMS.services.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




@RestController
@RequestMapping("/instructor")
public class InstructorController

{
    @Autowired
    private CourseService courseService;
    private LessonService lessonService;

    private static final String UPLOAD_DIRECTORY = "C:/uploads/";


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
    // manages course content
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("/{courseId}/update")
    public ResponseEntity<String> updateCourse(@PathVariable Long courseId, @RequestBody CourseModel updatedCourse) {
        courseService.updateCourseDetails(courseId, updatedCourse);
        return ResponseEntity.ok("Course updated successfully");
    }
    //removes students from courses.
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{courseId}/deleteStudent/{studentId}")
    public ResponseEntity<String> deleteEnrollStudent(@PathVariable Long courseId, @PathVariable Integer studentId) {
        courseService.deleteStudentFromCourse(courseId, studentId);
        return ResponseEntity.ok("Student deleted successfully");
    }
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping("/{courseId}/deleteAllStudents")
    public ResponseEntity<String> deleteAllStudents(@PathVariable Long courseId) {
        courseService.deleteAllStudentsFromCourse(courseId);
        return ResponseEntity.ok("All students deleted successfully");
    }
    // can upload media files
    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping("/{courseId}/upload-media")
    public ResponseEntity<String> uploadMedia(@PathVariable String courseId, @RequestParam("file") MultipartFile file) {
        try {

            File uploadDir = new File(UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    return ResponseEntity.status(500).body("Failed to create upload directory.");
                }
            }

            // Save the file to the Path
            String filePath = UPLOAD_DIRECTORY + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            // Add the file path
            courseService.addMediaFile(courseId, filePath);

            return ResponseEntity.ok("Media file uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }

    }




}
