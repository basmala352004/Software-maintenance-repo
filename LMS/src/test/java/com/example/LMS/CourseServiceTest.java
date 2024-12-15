package com.example.LMS;

import com.example.LMS.models.Course;
import com.example.LMS.models.CourseDB;
import com.example.LMS.models.Lesson;
import com.example.LMS.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseDB courseDB;

    private Course testCourse;
    private Lesson testLesson;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample course data
        testCourse = new Course("C001", "Java Basics", "Introduction to Java", 3, new ArrayList<>(), new ArrayList<>());

        // Sample lesson data (ensure the lesson class is aligned with your constructor)
        testLesson = new Lesson("C001", "Introduction to Java Lesson", new String[]{"Java basics"}, "Introduction to Java concepts",
                "John Doe", null, 45, null, "OTP123");
    }

    @Test
    public void testCreateCourse() {
        // Arrange
        List<Course> courses = new ArrayList<>(); // Create a mock list of courses
        when(courseDB.getListCourses()).thenReturn(courses); // Mock that getListCourses returns this list

        // Act
        courseService.createCourse(testCourse); // Call the method under test

        // Assert
        // Verify that the course was added to the list of courses
        assertEquals(1, courseDB.getListCourses().size());
        assertEquals("C001", courseDB.getListCourses().get(0).getCourseId());

        // Optionally, verify if createCourse was actually called on courseDB
        verify(courseDB, times(1)).createCourse(testCourse);
    }


    @Test
    public void testDisplayCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(testCourse);
        when(courseDB.getListCourses()).thenReturn(courses);


        List<Course> result = courseService.displayCourses();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java Basics", result.get(0).getTitle());
    }

    @Test
    public void testAddLessonToCourse() {
        when(courseDB.getCourseById("C001")).thenReturn(testCourse);


        courseService.addLessonToCourse(testLesson);

        // Assert
        assertNotNull(testCourse.getListLessons());
        assertEquals(1, testCourse.getListLessons().size());
        assertEquals("Introduction to Java Lesson", testCourse.getListLessons().get(0).getTitle());
        verify(courseDB, times(1)).getCourseById("C001");
    }

    @Test
    public void testAddMediaFileToCourse() {
        String filePath = "uploads/video.mp4";
        when(courseDB.getCourseById("C001")).thenReturn(testCourse);


        courseService.addMediaFile("C001", filePath);

        // Assert
        assertTrue(testCourse.getMediaFiles().contains(filePath));
        verify(courseDB, times(1)).addMediaFileToCourse("C001", filePath);
    }
}
