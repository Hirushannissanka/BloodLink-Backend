package com.bloodlink.notification.dto;

public record NotificationRequest(Long donorId, Long requestId, String email, String phone, String message) {
}
