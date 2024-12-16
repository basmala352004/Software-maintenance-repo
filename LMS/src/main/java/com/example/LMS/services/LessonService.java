package com.example.LMS.services;

import com.example.LMS.models.LessonModel;
import com.example.LMS.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public void createLesson(LessonModel lessonModel) {
        lessonRepository.save(lessonModel); // Persist the LessonModel
    }

    public List<LessonModel> displayLessons() {
        return lessonRepository.findAll(); // Retrieve all lessons from the database
    }
}
