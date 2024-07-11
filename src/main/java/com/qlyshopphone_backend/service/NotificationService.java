package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.NotificationDTO;
import com.qlyshopphone_backend.model.Notification;
import com.qlyshopphone_backend.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getNotifications();

    void saveNotification(String message, Users users);

    void deleteNotification();
}
