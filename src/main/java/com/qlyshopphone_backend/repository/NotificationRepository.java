package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    void deleteByTimestampBefore(LocalDateTime cutofDate);
}
