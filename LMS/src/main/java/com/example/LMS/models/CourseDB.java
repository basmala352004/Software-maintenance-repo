package com.example.LMS.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseDB {
    List<Course> listCourses;

    public CourseDB() {
        listCourses = new ArrayList<Course>();
    }
    public void createCourse(Course course) {
        listCourses.add(course);
    }
    public List<Course> getListCourses() {

        return listCourses;
    }
}
