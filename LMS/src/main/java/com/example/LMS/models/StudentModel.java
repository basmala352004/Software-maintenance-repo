package com.example.LMS.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class StudentModel extends User {

    @Column
    String name;
    @Column(unique=true)
    String email;

    public StudentModel() {
    }


    public StudentModel( Integer id,String email, String name) {
        super(id);
        this.email = email;
        this.name = name;
    }
    public StudentModel( String email, String name) {
        this.email = email;
        this.name = name;
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
    @JsonIgnore // Prevent serialization of the circular reference
    private List<CourseModel> courses;

    public List<CourseModel> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseModel> courses) {
        this.courses = courses;
    }
}
