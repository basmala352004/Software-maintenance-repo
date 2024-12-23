package com.example.LMS.controllers;

import com.example.LMS.DTOs.StudentDTO;
import com.example.LMS.models.Assignment;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.QuizModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.services.AssignmentService;
import com.example.LMS.services.QuizService;
import com.example.LMS.services.StudentService;
import com.example.LMS.services.TrackPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController
{
    final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @Autowired
    private TrackPerformanceService trackPerformanceService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private QuizService quizService;
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
    @PreAuthorize("hasRole('STUDENT')")
    // Endpoint for fetching assignment grades and feedback
    @GetMapping(value = "/assignments/{assignmentId}")
    public ResponseEntity<Map<String, Object>> getAssignmentGrades(@PathVariable Integer assignmentId) {
        Map<String, Object> assignmentGrades = trackPerformanceService.getAssignmentGrades(assignmentId);
        return ResponseEntity.ok(assignmentGrades);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping(value = "/quizes/{QuizId}")
    public ResponseEntity<Map<String, Object>> getQuizGrades(@PathVariable long QuizId) {
        Map<String, Object> quizGrades = trackPerformanceService.getQuizGrades(QuizId);
        return ResponseEntity.ok(quizGrades);
    }
    //submit quiz , assignment
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/submitAssignment")
    public ResponseEntity<Assignment> submitAssignment(@RequestBody Assignment assignment) {
        Assignment submittedAssignment = assignmentService.submitAssignment(assignment);
        return ResponseEntity.ok(submittedAssignment);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/submitQuiz")
    public ResponseEntity<QuizModel> submitQuiz(@RequestBody QuizModel quiz) {
        QuizModel submittedQuiz = quizService.submitQuiz(quiz);
        return ResponseEntity.ok(submittedQuiz);
    }
}
