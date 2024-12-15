package com.example.LMS.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonDB {
    List<Lesson> listLessons;
    public LessonDB() {
        listLessons = new ArrayList<Lesson>();
    }
    public void addLesson(Lesson lesson) {
        listLessons.add(lesson);
    }
    public List<Lesson> getListLessons() {
        return listLessons;
    }
}
