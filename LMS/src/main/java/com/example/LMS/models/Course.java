package com.example.LMS.models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private List<Lesson> listLessons;

    public Course(String courseId, List<Lesson> listLessons) {
        this.courseId = courseId;
        this.listLessons = listLessons;
    }
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public void addLesson(Lesson lesson) {
        listLessons.add(lesson);
    }
    public List<Lesson> getListLessons() {
        return listLessons;
    }
    public void setListLessons(List<Lesson> listLessons) {

        this.listLessons = listLessons;
    }
}