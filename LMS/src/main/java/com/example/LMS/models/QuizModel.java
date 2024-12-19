package com.example.LMS.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuizModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizTitle;

    // Many-to-One relationship with CourseModel (Each quiz belongs to one course)
    @ManyToOne
    @JoinColumn(name = "course_id") // The foreign key column in the quiz table
    private CourseModel course;

    // One-to-many relationship with QuestionModel (Each quiz has multiple questions)
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionModel> questions = new ArrayList<>();

    // Constructors, getters, and setters
    public QuizModel() {}

    public QuizModel(String quizTitle, CourseModel course) {
        this.quizTitle = quizTitle;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    public void addQuestion(QuestionModel question) {
        this.questions.add(question);
        question.setQuiz(this); // Set the quiz for this question
    }
}
