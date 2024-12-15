package com.example.LMS.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CourseDB {
    private List<Course> listCourses;

    public CourseDB() {
        this.listCourses = new ArrayList<>();
    }

    public void createCourse(Course course) {
        listCourses.add(course);
    }

    public List<Course> getListCourses() {
        return listCourses;
    }

    public Course getCourseById(String courseId) {
        for (Course course : listCourses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}
