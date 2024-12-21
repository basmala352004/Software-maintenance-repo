package com.example.LMS.repositories;


import com.example.LMS.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {
    // Custom query to find assignments by  assignmentID
    List<Assignment> findByAssignmentID(Integer  assignmentID);
}
