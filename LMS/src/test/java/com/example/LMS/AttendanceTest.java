package com.example.LMS;

import com.example.LMS.models.LessonModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.repositories.LessonRepository;
import com.example.LMS.repositories.StudentRepository;
import com.example.LMS.services.AttendanceService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AttendanceTest {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentRepository studentRepository;

    private StudentModel student;
    private LessonModel lesson;

    @BeforeEach
    void setUp() {
        student = new StudentModel("Amr", "amr.mohammed@gmail.com");
        lesson = new LessonModel("c++#1", Arrays.asList("Data types", "Varibles"), "Introduction to c++ basics", "Dr.Ali", LocalDateTime.of(2024, 1, 15, 10, 30), 60, "LESS#120");
        studentRepository.save(student);
        lessonRepository.save(lesson);
    }

    @Test
    public void lessonNotOngoingTest() {
        String result = attendanceService.attendLesson(student.getId(), lesson.getId(), lesson.getOTP());
        assertEquals("Lesson is not ongoing.", result);
    }
    @Test
    public void OTPNotCorrectTest() {
        lesson.setStartDate(LocalDateTime.now());
        String result = attendanceService.attendLesson(student.getId(), lesson.getId(), "NotValidOTP");
        assertEquals("OTP is not correct.", result);
    }
    @Test
    public void attendLessonSuccessfullyTest() {
        lesson.setStartDate(LocalDateTime.now());
        String result = attendanceService.attendLesson(student.getId(), lesson.getId(), lesson.getOTP());
        assertEquals("You are successfully attend.", result);
    }
    @Test
    public void lessonNotFoundTest() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> attendanceService.attendLesson(student.getId(), -1, lesson.getOTP()));
        assertEquals("Lesson not found", exception.getMessage());
    }
    @Test
    public void studentNotFoundTest() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> attendanceService.attendLesson(-1, lesson.getId(), lesson.getOTP()));
        assertEquals("Lesson not found", exception.getMessage());
    }
}
