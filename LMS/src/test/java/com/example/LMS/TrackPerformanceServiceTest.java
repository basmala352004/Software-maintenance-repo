package com.example.LMS;

import com.example.LMS.models.*;
import com.example.LMS.repositories.*;
import com.example.LMS.services.TrackPerformanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPerformanceForCourses() {
        // Arrange: Mock course, lesson, and attendance data
        String courseName = "Mathematics";
        String lessonName = "Lesson 1";

        CourseModel course = new CourseModel();
        course.setId(1L);
        course.setTitle(courseName);

        LessonModel lesson = new LessonModel();
        lesson.setId(1L);
        lesson.setTitle(lessonName);

        StudentModel student = new StudentModel();
        student.setId(1);
        student.setName("John Doe");

        AttendanceModel attendance = new AttendanceModel();
        attendance.setId(1L);
        attendance.setStudent(student);
        attendance.setLesson(lesson);
        attendance.setTimestamp(LocalDateTime.now());
        attendance.setAttended(true);

        course.setListLessons(List.of(lesson));

        when(courseRepository.findByTitle(courseName)).thenReturn(Optional.of(course));
        when(attendanceRepository.findByLesson(lesson)).thenReturn(List.of(attendance));

        // Act: Call the service method
        List<Map<String, Object>> result = trackPerformanceService.getPerformanceForCourses(
                List.of(courseName), lessonName);

        // Assert: Verify the response
        assertEquals(1, result.size());
        assertEquals(courseName, result.get(0).get("Course"));

        List<Map<String, Object>> performance = (List<Map<String, Object>>) result.get(0).get("Performance");
        assertEquals(1, performance.size());
        assertEquals(student.getId(), performance.get(0).get("Student ID"));
        assertEquals("Attended", performance.get(0).get("Attendance Status"));
        assertEquals(lessonName, performance.get(0).get("Lesson"));

        // Verify interactions with mocks
        verify(courseRepository, times(1)).findByTitle(courseName);
        verify(attendanceRepository, times(1)).findByLesson(lesson);
    }

    @Test
    void testGetPerformanceForCourses_NoCoursesFound() {
        // Arrange
        String courseName = "Unknown Course";
        when(courseRepository.findByTitle(courseName)).thenReturn(Optional.empty());

        // Act
        List<Map<String, Object>> result = trackPerformanceService.getPerformanceForCourses(
                List.of(courseName), "Lesson 1");

        // Assert
        assertEquals(0, result.size());

        // Verify interactions with mocks
        verify(courseRepository, times(1)).findByTitle(courseName);
        verifyNoInteractions(attendanceRepository);
    }

    @Test
    void testGetPerformanceForCourses_NoLessonsFound() {
        // Arrange
        String courseName = "Mathematics";
        String lessonName = "Unknown Lesson";

        CourseModel course = new CourseModel();
        course.setId(1L);
        course.setTitle(courseName);
        course.setListLessons(Collections.emptyList());

        when(courseRepository.findByTitle(courseName)).thenReturn(Optional.of(course));

        // Act
        List<Map<String, Object>> result = trackPerformanceService.getPerformanceForCourses(
                List.of(courseName), lessonName);

        // Assert
        assertEquals(0, result.size());

        // Verify interactions with mocks
        verify(courseRepository, times(1)).findByTitle(courseName);
        verifyNoInteractions(attendanceRepository);
    }
}

