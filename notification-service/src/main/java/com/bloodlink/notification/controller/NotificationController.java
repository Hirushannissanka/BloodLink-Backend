package com.bloodlink.notification.controller;

import com.bloodlink.notification.dto.NotificationRequest;
import com.bloodlink.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/internal/notifications")
    public void queue(@RequestBody NotificationRequest request) {
        service.queue(request);
    }
}
