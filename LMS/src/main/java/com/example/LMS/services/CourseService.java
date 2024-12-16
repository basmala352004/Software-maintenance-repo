package com.example.LMS.services;

import com.example.LMS.models.CourseModel;
import com.example.LMS.models.LessonModel;
import com.example.LMS.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public void createCourse(CourseModel course) {
        courseRepository.save(course);  // Save the course to the database
    }

    public List<CourseModel> displayCourses() {
        return courseRepository.findAll();  // Retrieve all courses from the database
    }

    public void addLessonToCourse(Long courseId, LessonModel lesson) {
        CourseModel course = courseRepository.findById(courseId).orElse(null);  // Find the course by ID
        if (course != null) {
            lesson.setCourseModel(course);  // Set the course model in the lesson
            course.addLesson(lesson);  // Add the lesson to the course
            courseRepository.save(course);  // Save the updated course back to the database
        }
    }

    public void addMediaFile(String courseId, String filePath) {
        CourseModel course = courseRepository.findByCourseId(courseId);
        if (course != null) {
            course.getMediaFiles().add(filePath);  // Add media file to the course
            courseRepository.save(course);  // Save the updated course
        }
    }
}
