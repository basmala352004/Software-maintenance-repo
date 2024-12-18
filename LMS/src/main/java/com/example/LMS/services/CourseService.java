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
        courseRepository.save(course);
    }

    public List<CourseModel> displayCourses() {
        return courseRepository.findAll();
    }

    public void addLessonToCourse(Long courseId, LessonModel lesson) {
        CourseModel course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            lesson.setCourseModel(course);
            course.addLesson(lesson);
            courseRepository.save(course);
        }
    }

    public void addMediaFile(String courseId, String filePath) {
        CourseModel course = courseRepository.findByCourseId(courseId);
        if (course != null) {
            course.getMediaFiles().add(filePath);
            courseRepository.save(course);
        }
    }
}