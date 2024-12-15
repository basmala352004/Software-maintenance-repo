package com.example.LMS.services;


import com.example.LMS.models.Course;
import com.example.LMS.models.CourseDB;
import com.example.LMS.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseDB courseDB;
    @Autowired
    private LessonService lessonService;

    public void createCourse(Course course) {
        courseDB.createCourse(course);
        for (int i = 0; i < course.getListLessons().size(); i++) {
            lessonService.createLesson(course.getListLessons().get(i));
        }
    }
    public void addLessonToCourse(Lesson lesson) {
        String courseId = lesson.getCourseId();
        for (int i = 0; i < courseDB.getListCourses().size(); i++) {
            Course currCourse = courseDB.getListCourses().get(i);
            if (currCourse.getCourseId().equals(courseId)) {
                currCourse.addLesson(lesson);
            }
        }
    }
    public List<Course> displayCourses() {
        return courseDB.getListCourses();
    }
}
