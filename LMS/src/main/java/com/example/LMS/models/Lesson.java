package com.example.LMS.models;

import java.time.LocalDateTime;

public class Lesson {
    private String courseId;
    private String title;
    private String[] topics;
    private String description;
    private String teacherName;
    private LocalDateTime startDate;
    private int durationMinutes;
    private LocalDateTime endDate;
    private String OTP;

    public Lesson(String courseId, String title, String[] topics, String description, String teacherName, LocalDateTime startDate, int durationMinutes, LocalDateTime endDate, String OTP) {
        this.courseId = courseId;
        this.title = title;
        this.topics = topics;
        this.description = description;
        this.teacherName = teacherName;
        this.startDate = startDate;
        this.durationMinutes = durationMinutes;
        this.endDate = endDate;
        this.OTP = OTP;
    }
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String CoursId) {
        this.courseId = CoursId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String[] getTopics() {
        return topics;
    }
    public void setTopics(String[] topics) {
        this.topics = topics;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public String getOTP() {
        return OTP;
    }
    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}

