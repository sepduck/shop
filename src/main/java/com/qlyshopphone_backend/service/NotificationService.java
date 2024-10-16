package com.qlyshopphone_backend.service;

import com.qlyshopphone_backend.dto.request.NotificationRequest;
import com.qlyshopphone_backend.model.Users;

import java.util.List;

public interface NotificationService {
    List<NotificationRequest> getNotifications();

    void saveNotification(String message, Users users);

    void deleteNotification();
}
