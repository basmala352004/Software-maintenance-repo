package com.example.LMS.services;

import com.example.LMS.models.*;
import com.example.LMS.repositories.LessonRepository;
import com.example.LMS.repositories.NotificationRepository;
import com.example.LMS.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationService notificationService;

    public void createLesson(LessonModel lessonModel) {
        lessonModel.updateEndDate();
        lessonRepository.save(lessonModel);
    }

    public List<LessonModel> displayLessons() {
        return lessonRepository.findAll();
    }

    public String generateOTP(String OTP, long lessonId) {
        LessonModel lesson = lessonRepository.findById(lessonId).get();
        lesson.setOTP(OTP);
        lessonRepository.save(lesson);

        String notificationType = "Lesson OTP";
        String notificationMessage = "OTP for lesson with ID " + lessonId + " '" + lesson.getTitle() + "' has been generated " + OTP;
        CourseModel course = lesson.getCourseModel();
        List<StudentModel> usersToNotify = course.getStudents();
        NotificationModel notification;
        if (!usersToNotify.isEmpty()) {
            for (StudentModel student : usersToNotify) {
                notification = new NotificationModel(student, notificationType, notificationMessage, LocalDateTime.now());
                notificationService.sendNotification(notification);
            }
        }


        return "OTP generated succeffully and notification has sent to students.";
    }
}
