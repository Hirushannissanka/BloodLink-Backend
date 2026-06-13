package com.v1.patient_request.dto.response;

import com.v1.patient_request.entity.BloodType;
import lombok.Data;

import java.time.LocalDateTime;

// DTO Pattern: decouples PatientProfile entity from API response
@Data
public class PatientProfileResponse {
    private Long id;
    private Long userId;
    private BloodType bloodType;
    private String city;
    private String district;
    private String emergencyContact;
    private String medicalNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
