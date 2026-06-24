package com.bloodlink.auth.dto;

public record PatientProfileRequest(
        String bloodType,
        String city,
        String district,
        String emergencyContact,
        String medicalNotes
) {
}
