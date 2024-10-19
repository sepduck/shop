package com.qlyshopphone_backend.repository;

import com.qlyshopphone_backend.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {
    void deleteByTimestampBefore(LocalDateTime cutofDate);
}
