package com.example.LMS.services;

import com.example.LMS.DTOs.CourseDTO;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.LessonModel;
import com.example.LMS.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public void createCourse(CourseModel course) {
        courseRepository.save(course);
    }

    public List<CourseDTO> displayCourses() {
        List <CourseModel> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS= courses.stream().map(c->{
            CourseDTO x= new CourseDTO(c);
            x.setStudents(c.getStudents());
            return x;
        }).collect(Collectors.toList());
        return courseDTOS;
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
