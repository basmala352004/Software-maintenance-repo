package com.example.LMS.repositories;

import com.example.LMS.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, Long> {
}
