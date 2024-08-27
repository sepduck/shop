package com.qlyshopphone_backend.controller.rest;
import static com.qlyshopphone_backend.constant.PathConstant.*;

import com.qlyshopphone_backend.dto.NotificationDTO;
import com.qlyshopphone_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestNotificationController {
    private final NotificationService notificationService;

    @GetMapping(ADMIN_NOTIFICATION)
    public ResponseEntity<?> getNotification() {
        List<NotificationDTO> notificationDTOS = notificationService.getNotifications();
        if (notificationDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificationDTOS);
    }
}
