package com.v1.patient_request.dto.request;

import com.v1.patient_request.entity.BloodType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// DTO Pattern: decouples API input from the BloodRequest entity
@Data
public class BloodRequestRequest {

    @NotNull(message = "Blood type needed is required")
    private BloodType bloodTypeNeeded;

    @NotNull(message = "Units needed is required")
    @Min(value = 1, message = "At least 1 unit must be requested")
    private Integer unitsNeeded;

    @NotBlank(message = "Urgency level is required")
    private String urgencyLevel; // CRITICAL | URGENT | NORMAL

    @NotBlank(message = "Hospital name is required")
    private String hospitalName;

    private String hospitalAddress;

    @NotBlank(message = "City is required")
    private String city;

    private String district;

    private String notes;
}
