package com.example.LMS.repositories;

import com.example.LMS.models.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionModel, Long> {

}
