package com.example.LMS.controllers;



import com.example.LMS.models.Lesson;
import com.example.LMS.services.CourseService;
import com.example.LMS.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-lesson")
    public void createLesson(@RequestBody Lesson lesson) {
        lessonService.createLesson(lesson);
        courseService.addLessonToCourse(lesson);
    }
    @GetMapping("/display-lessons")
    public ResponseEntity<List<Lesson>> displayLessons() {

        return ResponseEntity.ok(lessonService.displayLessons());
    }
}
