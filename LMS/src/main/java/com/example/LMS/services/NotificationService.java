package com.example.LMS.services;

import com.example.LMS.models.NotificationModel;
import com.example.LMS.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    // Fetch unread notifications for a specific user
    public List<NotificationModel> fetchUnreadNotifications(Integer userID) {
        return notificationRepository.findByUserIDAndIsReadFalse(userID);
    }

    // Fetch all notifications for a specific user
    public List<NotificationModel> fetchAllNotifications(Integer userID) {
        return notificationRepository.findByUserID(userID);
    }
    // Send or save a new notification
    public void sendNotification(NotificationModel notification) {
        notificationRepository.save(notification);
    }

    public void markAsRead(Integer notificationID) {
        NotificationModel notification = notificationRepository.findById(notificationID).orElse(null);
        if (notification != null) {
            notification.setIsRead(true);  // Mark the notification as read
            notificationRepository.save(notification);  // Save the updated notification to the database
        }
    }

}
