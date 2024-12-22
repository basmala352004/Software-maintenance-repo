package com.example.LMS.controllers;
import com.example.LMS.models.NotificationModel;
import com.example.LMS.models.User;
import com.example.LMS.services.NotificationService;
import com.example.LMS.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    UserRepository userRepository;

    // Fetch unread notifications for a specific user
    @GetMapping("/unread/{userId}")
    public List<NotificationModel> getUnreadNotifications(@PathVariable Integer userId) {
        return notificationService.fetchUnreadNotifications(userId);
    }
    // Fetch all notifications for a specific user
    @GetMapping("/all/{userId}")
    public List<NotificationModel> getAllNotifications(@PathVariable Integer userId) {
        return notificationService.fetchAllNotifications(userId);
    }
    // Send a new notification
    @PostMapping("/send")
    public String sendNotification(@RequestBody Map<String, Object> payload) {
        Integer userId = (Integer) payload.get("userId");
        String type = (String) payload.get("type");
        String message = (String) payload.get("message");
        String timestampStr = (String) payload.get("timestamp");
        LocalDateTime timestamp = timestampStr != null ? LocalDateTime.parse(timestampStr) : LocalDateTime.now();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        NotificationModel notification = new NotificationModel(user, type, message, timestamp);
        notificationService.sendNotification(notification);
        return "Notification sent successfully!";
    }
    // Mark a notification as read
    @PutMapping("/markAsRead/{notificationID}")
    public String markAsRead(@PathVariable Integer notificationID) {
        notificationService.markAsRead(notificationID);
        return "Notification marked as read!";
    }
}