package com.v1.patient_request.dto.request;

import com.v1.patient_request.entity.BloodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO Pattern: decouples API input from the PatientProfile entity
@Data
public class PatientRequest {

    @NotNull(message = "Blood type is required")
    private BloodType bloodType;

    @NotBlank(message = "City is required")
    private String city;

    private String district;

    private String emergencyContact;

    private String medicalNotes;
}

