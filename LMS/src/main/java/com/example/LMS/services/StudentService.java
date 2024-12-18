package com.example.LMS.services;

import com.example.LMS.DTOs.StudentDTO;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.repositories.CourseRepository;
import com.example.LMS.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public void createStudent(StudentModel s) {
        studentRepository.save(s);
    }
    public List<StudentDTO> getAllStudents() {
        List<StudentModel> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = students.stream().map(s -> {
            StudentDTO x = new StudentDTO(s);
            x.setCourses(s.getCourses());
            return x;
        }).collect(Collectors.toList());

        return studentDTOS;
    }
    public StudentModel getStudentById(int id) {
        return studentRepository.findById(id).get();
    }
    public void deleteStudentById(int id) {
        studentRepository.deleteById(id);
    }
    public void updateStudent(StudentModel s) {
        studentRepository.save(s);
    }
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
    public StudentModel enrollStudent( int studentId, long csid) {
        // اوزين نجيب ال طالب العنده ال
        StudentModel s = studentRepository.findById(studentId).get();
        if(s == null) {
            return null;
        }
        CourseModel course = courseRepository.findById(csid).get();
        if(course == null) {
            return null;
        }
        if(s.getCourses().contains(course)) {
            return null;
        }
        s.getCourses().add(course);
        studentRepository.save(s);
        course.getStudents().add(s);
        courseRepository.save(course);
        return s;
    }
}
