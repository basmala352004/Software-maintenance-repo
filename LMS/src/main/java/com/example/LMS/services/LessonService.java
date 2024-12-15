package com.example.LMS.services;


import com.example.LMS.models.Course;
import com.example.LMS.models.Lesson;
import com.example.LMS.models.LessonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonDB lessonDB;

    public void createLesson(Lesson lesson) {
        lessonDB.addLesson(lesson);
    }
    public List<Lesson> displayLessons() {
        List<Lesson> lessons = lessonDB.getListLessons();
        return lessonDB.getListLessons();
    }

}
