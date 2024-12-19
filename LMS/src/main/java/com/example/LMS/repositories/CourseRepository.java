package com.example.LMS.repositories;

import com.example.LMS.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseModel, Long> {
    CourseModel findByCourseId(String courseId);
     Optional<CourseModel> findByTitle(String title);
}
