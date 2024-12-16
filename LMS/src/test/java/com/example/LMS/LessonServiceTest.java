package com.example.LMS;

import com.example.LMS.models.LessonModel;
import com.example.LMS.repositories.LessonRepository;
import com.example.LMS.services.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Enables Mockito for this test class
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonService lessonService;

    private LessonModel lesson;

    @BeforeEach
    void setUp() {
        // Create a LessonModel for testing
        lesson = new LessonModel("Lesson 1", Arrays.asList("Topic 1", "Topic 2"), "Lesson Description", "Dr. John", null, 60, null, "1234");
    }

    @Test
    void testCreateLesson() {
        // Mock save method
        when(lessonRepository.save(lesson)).thenReturn(lesson);

        lessonService.createLesson(lesson);  // Call the service method

        verify(lessonRepository, times(1)).save(lesson);  // Verify that save was called once
    }

    @Test
    void testDisplayLessons() {
        // Mock the repository to return a list of lessons
        when(lessonRepository.findAll()).thenReturn(Arrays.asList(lesson));

        List<LessonModel> lessons = lessonService.displayLessons();

        assertNotNull(lessons);
        assertEquals(1, lessons.size());
        assertEquals("Lesson 1", lessons.get(0).getTitle());
    }
}
