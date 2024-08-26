package com.qlyshopphone_backend.controller.rest;

import com.qlyshopphone_backend.dto.NotificationDTO;
import com.qlyshopphone_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestNotificationController {
    private final NotificationService notificationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @GetMapping("/notification")
    public ResponseEntity<?> getNotification() {
        List<NotificationDTO> notificationDTOS = notificationService.getNotifications();
        if (notificationDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificationDTOS);
    }
}
