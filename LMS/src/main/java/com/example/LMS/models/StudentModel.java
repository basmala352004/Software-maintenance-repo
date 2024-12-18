package com.example.LMS.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

@Entity
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column
    String name;
    @Column(unique=true)
    String email;

    public StudentModel(String email, String name) {
        this.email = email;
        this.name = name;
    }
    public StudentModel() {
    }

    public StudentModel(int id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @ManyToMany(mappedBy = "students")

    List<CourseModel>courses;

    public List<CourseModel> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseModel> courses) {
        this.courses = courses;
    }
}
