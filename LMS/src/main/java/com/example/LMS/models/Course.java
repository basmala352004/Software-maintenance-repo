package com.example.LMS.models;

import java.util.List;

public class Course {
    private String courseId;
    private String title;
    private String description;
    private int durationMinutes; // Add duration field
    private List<Lesson> listLessons;

    // Constructor
    public Course(String courseId, String title, String description, int durationMinutes, List<Lesson> listLessons) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.listLessons = listLessons;
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    public List<Lesson> getListLessons() {
        return listLessons;
    }
    public void setListLessons(List<Lesson> listLessons) {
        this.listLessons = listLessons;
    }

    public void addLesson(Lesson lesson) {
        this.listLessons.add(lesson);
    }
}
