package com.example.LMS.services;

import com.example.LMS.models.Course;
import com.example.LMS.models.CourseDB;
import com.example.LMS.models.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseDB courseDB;

    public void createCourse(Course course) {
        courseDB.createCourse(course);
    }

    public List<Course> displayCourses() {
        return courseDB.getListCourses();
    }

    public void addLessonToCourse(Lesson lesson) {
        Course course = courseDB.getCourseById(lesson.getCourseId());
        if (course != null) {
            course.addLesson(lesson);
        }
    }
}
