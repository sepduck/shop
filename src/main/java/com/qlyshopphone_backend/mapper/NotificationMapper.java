package com.qlyshopphone_backend.mapper;

import com.qlyshopphone_backend.dto.NotificationDTO;
import com.qlyshopphone_backend.model.Notification;
import com.qlyshopphone_backend.model.Users;

public class NotificationMapper {
    public static NotificationDTO toDto(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(notification.getNotificationId());
        notificationDTO.setUserId(notification.getUser().getUserId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setTimestamp(notification.getTimestamp());
        return notificationDTO;
    }

    public static Notification toEntity(NotificationDTO notificationDTO, Users user) {
        Notification notification = new Notification();
        notification.setNotificationId(notificationDTO.getNotificationId());
        notification.setUser(user);
        notification.setMessage(notificationDTO.getMessage());
        notification.setTimestamp(notificationDTO.getTimestamp());
        return notification;
    }
}
