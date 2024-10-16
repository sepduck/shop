package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.NotificationRequest;
import com.qlyshopphone_backend.model.Notification;
import com.qlyshopphone_backend.model.Users;

public class NotificationMapper {
    public static NotificationRequest toDto(Notification notification) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setNotificationId(notification.getNotificationId());
        notificationRequest.setUserId(notification.getUser().getUserId());
        notificationRequest.setMessage(notification.getMessage());
        notificationRequest.setTimestamp(notification.getTimestamp());
        return notificationRequest;
    }

    public static Notification toEntity(NotificationRequest notificationRequest, Users user) {
        Notification notification = new Notification();
        notification.setNotificationId(notificationRequest.getNotificationId());
        notification.setUser(user);
        notification.setMessage(notificationRequest.getMessage());
        notification.setTimestamp(notificationRequest.getTimestamp());
        return notification;
    }
}
