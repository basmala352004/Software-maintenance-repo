package com.example.LMS.controllers;

import com.example.LMS.DTOs.StudentDTO;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.services.StudentService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController
{
    final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity< List<StudentDTO> >retrieveAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentModel>  retrieveStudentById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/enrollCourse")
    public ResponseEntity<String> enrollCourse(@RequestParam("student_id") int studentId, @RequestParam("Course_id") long courseid) {
        System.out.println(studentId);
        studentService.enrollStudent(studentId ,  courseid);
        return  ResponseEntity.ok("Student Enroll successfully ");

    }
}
