package com.example.LMS;

import com.example.LMS.DTOs.StudentDTO;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.repositories.CourseRepository;
import com.example.LMS.repositories.StudentRepository;
import com.example.LMS.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Enables Mockito for this test class
public class StudentTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentModel student;
    private CourseModel course;


    @BeforeEach
    void setUp() {
        student = new StudentModel(1, "john.doe@example.com", "John Doe");
        student.setCourses(new ArrayList<>());  // Initialize the courses list

        course = new CourseModel("CS101", "Computer Science 101", "Intro to CS", 40, null, null);
        course.setStudents(new ArrayList<>());  // Initialize the students list
    }



    @Test
    void testCreateStudent() {
        // Mock save method
        when(studentRepository.save(student)).thenReturn(student);

        studentService.createStudent(student);  // Call the service method

        verify(studentRepository, times(1)).save(student);  // Verify that save was called once
    }

    @Test
    void testGetAllStudents() {
        // Mock findAll method
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        List<StudentDTO> students = studentService.getAllStudents();  // Call the service method

        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals("John Doe", students.get(0).getName());
    }

    @Test
    void testGetStudentById() {
        // Mock findById method
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        StudentModel foundStudent = studentService.getStudentById(1);  // Call the service method

        assertNotNull(foundStudent);
        assertEquals("John Doe", foundStudent.getName());
        verify(studentRepository, times(1)).findById(1);  // Verify that findById was called once
    }

    @Test
    void testDeleteStudentById() {
        doNothing().when(studentRepository).deleteById(1);

        studentService.deleteStudentById(1);  // Call the service method

        verify(studentRepository, times(1)).deleteById(1);  // Verify that deleteById was called once
    }

    @Test
    void testUpdateStudent() {
        // Mock save method
        when(studentRepository.save(student)).thenReturn(student);

        studentService.updateStudent(student);  // Call the service method

        verify(studentRepository, times(1)).save(student);  // Verify that save was called once
    }

    @Test
    void testEnrollStudent() {
        // Mock the repositories to return student and course
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        studentService.enrollStudent(1, 1L);  // Call the service method

        // Verify that the student was added to the course's students list
        assertTrue(student.getCourses().contains(course));
        assertTrue(course.getStudents().contains(student));

        verify(studentRepository, times(1)).save(student);
        verify(courseRepository, times(1)).save(course);
    }

}
