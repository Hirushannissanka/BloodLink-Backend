package com.bloodlink.request.dto;

public record NotificationRequest(Long donorId, Long requestId, String email, String phone, String message) {
}
