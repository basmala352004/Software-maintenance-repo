package com.example.LMS.models;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    private String role;
    private String email;

    public User() {}
    public User(Integer id) {
        this.id = id;
    }
    public User(Integer id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
    public User(String name, String role, String password, String email) {
        this.name = name;
        this.role = role;
        this.password = password;
        this.email = email;
    }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public User(Integer id, String name, String password, String role, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}