package com.example.LMS.repositories;

import com.example.LMS.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseModel, Long> {
    CourseModel findByCourseId(String courseId);
}