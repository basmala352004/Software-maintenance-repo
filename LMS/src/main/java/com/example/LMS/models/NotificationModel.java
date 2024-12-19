package com.example.LMS.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationID;
    private Integer userID;
    // Many-to-One relationship with StudentModel
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private StudentModel student;

    private String type;
    private String message;
    private Date timestamp;
    private Boolean isRead;
    public NotificationModel() {}
    public NotificationModel(Integer userID, String type, String message, Date timestamp, Boolean isRead) {
        this.userID = userID;
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }
    public Integer getNotificationID() {
        return notificationID;
    }
    public void setNotificationID(Integer notificationID) {

        this.notificationID = notificationID;
    }
    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
