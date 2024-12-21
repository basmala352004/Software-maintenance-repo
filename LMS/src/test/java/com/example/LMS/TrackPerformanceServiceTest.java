package com.example.LMS.services;

import com.example.LMS.models.*;
import com.example.LMS.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackPerformanceServiceTest {

    @InjectMocks
    private TrackPerformanceService trackPerformanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPerformanceForCourses() {
        String courseName = "Math";
        String lessonName = "Lesson 1";

        CourseModel mockCourse = new CourseModel();
        mockCourse.setTitle(courseName);

        LessonModel mockLesson = new LessonModel();
        mockLesson.setTitle(lessonName);
        mockCourse.setListLessons(Collections.singletonList(mockLesson));

        AttendanceModel mockAttendance = new AttendanceModel();
        StudentModel mockStudent = new StudentModel();
        mockStudent.setId(1);
        mockStudent.setName("John Doe");
        mockAttendance.setStudent(mockStudent);
        mockAttendance.setAttended(true);
        mockAttendance.setTimestamp(LocalDateTime.now());
        mockAttendance.setLesson(mockLesson);

        when(courseRepository.findByTitle(courseName)).thenReturn(Optional.of(mockCourse));
        when(attendanceRepository.findByLesson(mockLesson)).thenReturn(Collections.singletonList(mockAttendance));

        List<Map<String, Object>> result = trackPerformanceService.getPerformanceForCourses(Collections.singletonList(courseName), lessonName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(courseName, result.get(0).get("Course"));
    }

    @Test
    void testGetAssignmentGrades() {
        int assignmentId = 1;

        Assignment mockAssignment = new Assignment();
        mockAssignment.setAssignmentID(assignmentId);
        mockAssignment.setTitle("Assignment 1");
        mockAssignment.setGrades(95);
        mockAssignment.setFeedback("Well done!");

        StudentModel mockStudent = new StudentModel();
        mockStudent.setId(1);
        mockStudent.setName("John Doe");

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(mockAssignment));
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));

        Map<String, Object> result = trackPerformanceService.getAssignmentGrades(assignmentId);

        assertNotNull(result);
        assertEquals(assignmentId, result.get("assignmentId"));
        assertEquals("Assignment 1", result.get("assignmentTitle"));
        assertNotNull(result.get("students"));
        assertEquals(1, ((List<?>) result.get("students")).size());
    }

    @Test
    void testGetAssignmentGrades_AssignmentNotFound() {
        int assignmentId = 1;

        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
                trackPerformanceService.getAssignmentGrades(assignmentId)
        );

        assertEquals("Assignment not found with ID: 1", exception.getMessage());
    }
}
