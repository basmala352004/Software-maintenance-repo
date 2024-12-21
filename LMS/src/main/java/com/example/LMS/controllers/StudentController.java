package com.example.LMS.controllers;

import com.example.LMS.DTOs.StudentDTO;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.services.StudentService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {
    final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public ResponseEntity< List<StudentDTO> >retrieveAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentModel>  retrieveStudentById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/enrollCourse")
    public ResponseEntity<String> enrollCourse(@RequestParam("student_id") int studentId, @RequestParam("Course_id") long courseid) {
        System.out.println(studentId);
        studentService.enrollStudent(studentId ,  courseid);
        return  ResponseEntity.ok("Student Enroll successfully ");

    }
}
