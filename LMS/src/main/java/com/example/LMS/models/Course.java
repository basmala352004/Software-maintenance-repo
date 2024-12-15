package com.example.LMS.models;

import java.util.List;

public class Course {
    private String courseId;
    private String title;
    private String description;
    private int durationHours; // Add duration field
    private List<Lesson> listLessons;
    private List<String> mediaFiles; // Paths to uploaded media files


    // Constructor
    public Course(String courseId, String title, String description, int durationHours, List<Lesson> listLessons,List<String> mediaFiles) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.durationHours = durationHours;
        this.listLessons = listLessons;
        this.mediaFiles = mediaFiles;

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
        return durationHours;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationHours = durationMinutes;
    }
    public List<Lesson> getListLessons() {
        return listLessons;
    }
    public void setListLessons(List<Lesson> listLessons) {
        this.listLessons = listLessons;
    }
    public List<String> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<String> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public void addLesson(Lesson lesson) {
        this.listLessons.add(lesson);
    }
}
