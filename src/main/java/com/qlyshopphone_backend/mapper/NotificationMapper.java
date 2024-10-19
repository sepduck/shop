package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.request.NotificationRequest;
import com.qlyshopphone_backend.model.Notifications;
import com.qlyshopphone_backend.model.Users;

public class NotificationMapper {
    public static NotificationRequest toDto(Notifications notifications) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setId(notifications.getId());
        notificationRequest.setUserId(notifications.getUsers().getId());
        notificationRequest.setMessage(notifications.getMessage());
        notificationRequest.setTimestamp(notifications.getTimestamp());
        return notificationRequest;
    }

    public static Notifications toEntity(NotificationRequest notificationRequest, Users users) {
        Notifications notifications = new Notifications();
        notifications.setId(notificationRequest.getId());
        notifications.setUsers(users);
        notifications.setMessage(notificationRequest.getMessage());
        notifications.setTimestamp(notificationRequest.getTimestamp());
        return notifications;
    }
}
