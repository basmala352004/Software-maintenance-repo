package com.example.LMS.controllers;

import com.example.LMS.models.NotificationModel;
import com.example.LMS.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Fetch unread notifications for a specific user
    @GetMapping("/unread/{userID}")
    public List<NotificationModel> getUnreadNotifications(@PathVariable Integer userID) {
        return notificationService.fetchUnreadNotifications(userID);
    }

    // Fetch all notifications for a specific user
    @GetMapping("/all/{userID}")
    public List<NotificationModel> getAllNotifications(@PathVariable Integer userID) {
        return notificationService.fetchAllNotifications(userID);
    }

    // Send a new notification
    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationModel notification) {
        notificationService.sendNotification(notification);
        return "Notification sent successfully!";
    }
    @PutMapping("/markAsRead/{notificationID}")
    public String markAsRead(@PathVariable Integer notificationID) {
        notificationService.markAsRead(notificationID);
        return "Notification marked as read!";
    }
}
