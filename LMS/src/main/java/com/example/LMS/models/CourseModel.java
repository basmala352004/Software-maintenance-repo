package com.example.LMS.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseId;
    private String title;
    private String description;
    private int durationHours;
    // relation to LessonModel
    @OneToMany(mappedBy = "courseModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonModel> listLessons = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "course_media_files", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "media_file")
    private List<String> mediaFiles; // Paths to uploaded media files

    // Constructor
    public CourseModel(String courseId, String title, String description, int durationHours, List<LessonModel> listLessons, List<String> mediaFiles) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.durationHours = durationHours;
        this.listLessons = listLessons;
        this.mediaFiles = mediaFiles != null ? mediaFiles : new ArrayList<>();
    }

    public CourseModel() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public List<LessonModel> getListLessons() {
        return listLessons;
    }

    public void setListLessons(List<LessonModel> listLessons) {
        this.listLessons = listLessons;
    }

    public List<String> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<String> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public void addLesson(LessonModel lesson) {
        this.listLessons.add(lesson);
    }
}