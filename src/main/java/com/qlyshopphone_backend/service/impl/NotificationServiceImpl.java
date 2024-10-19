package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.dto.request.NotificationRequest;
import com.qlyshopphone_backend.mapper.NotificationMapper;
import com.qlyshopphone_backend.model.Notifications;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.repository.NotificationRepository;
import com.qlyshopphone_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationRequest> getNotifications() {
        List<Notifications> notifications = notificationRepository.findAll();
        return notifications.stream()
                .sorted(Comparator.comparing(Notifications::getId).reversed())
                .map(NotificationMapper :: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveNotification(String message, Users users) {
        Notifications notifications = new Notifications();
        notifications.setMessage(message);
        notifications.setUsers(users);
        notifications.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notifications);
    }

    // Phương thức xóa các thông báo cũ hơn 15 ngày
    @Override
    @Scheduled(cron = "0 0 1 * * ?") // Chạy vào mỗi ngày lúc 00:01:00
    public void deleteNotification() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thresholdDate = now.minusDays(15);

        notificationRepository.deleteByTimestampBefore(thresholdDate);
    }
}
