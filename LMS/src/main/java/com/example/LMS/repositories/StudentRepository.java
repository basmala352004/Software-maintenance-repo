package com.example.LMS.repositories;

import com.example.LMS.models.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentModel,Integer> {
}
