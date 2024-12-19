package com.example.LMS;

import com.example.LMS.DTOs.StudentDTO;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.LessonModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.repositories.CourseRepository;
import com.example.LMS.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Enables Mockito for this test class
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private CourseModel course;
    private LessonModel lesson;

    @BeforeEach
    void setUp() {
        // Initialize students and lessons
        StudentModel student1 = new StudentModel("student1@example.com", "John Doe");
        StudentModel student2 = new StudentModel("student2@example.com", "Jane Smith");

        // Create a CourseModel and LessonModel for testing
        course = new CourseModel("CS101", "Computer Science 101", "Intro to CS", 40, null, null);
        course.setStudents(Arrays.asList(student1, student2));  // Adding students to the course
        course.setListLessons(new ArrayList<>());  // Initialize the lessons list

        lesson = new LessonModel("Lesson 1", Arrays.asList("Topic 1", "Topic 2"), "Lesson Description", "Dr. John",
                LocalDateTime.of(2024, 12, 19, 10, 0), 60, "1234");  // Setting a valid startDate
    }

    @Test
    void testCreateCourse() {
        // Mock save method
        when(courseRepository.save(course)).thenReturn(course);

        courseService.createCourse(course);  // Call the service method

        verify(courseRepository, times(1)).save(course);  // Verify that save was called once
    }

    @Test
    void testDisplayCourses() {
        // Mock the repository to return the course
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        // Call the displayCourses function
        var courses = courseService.displayCourses();

        // Verify the results
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals("CS101", courses.get(0).getCourseId());

        // Verify students in the DTO
        List<StudentDTO> returnedStudents = courses.get(0).getStudents();
        assertNotNull(returnedStudents);
        assertEquals(2, returnedStudents.size());
        assertEquals("John Doe", returnedStudents.get(0).getName());
        assertEquals("Jane Smith", returnedStudents.get(1).getName());
    }

    @Test
    void testAddLessonToCourse() {
        // Mock findById to return a course
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        courseService.addLessonToCourse(course.getId(), lesson);  // Call the service method

        assertEquals(1, course.getListLessons().size());  // Verify that the lesson was added
        assertEquals("Lesson 1", course.getListLessons().get(0).getTitle());
    }

    @Test
    void testAddMediaFile() {
        // Mock findByCourseId to return a course
        when(courseRepository.findByCourseId(course.getCourseId())).thenReturn(course);

        courseService.addMediaFile(course.getCourseId(), "C:/uploads/lesson1.mp4");  // Call the service method

        assertEquals(1, course.getMediaFiles().size());  // Verify that the media file was added
        assertEquals("C:/uploads/lesson1.mp4", course.getMediaFiles().get(0));
    }
}
