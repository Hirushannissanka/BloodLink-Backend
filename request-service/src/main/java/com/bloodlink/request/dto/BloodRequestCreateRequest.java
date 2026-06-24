package com.bloodlink.request.dto;

import jakarta.validation.constraints.*;

public record BloodRequestCreateRequest(
        @NotBlank String patientName,
        @NotBlank String contactPhone,
        @NotBlank String district,
        @NotBlank String bloodType,
        @Min(1) @Max(20) Integer units,
        @NotBlank String urgency,
        @NotBlank String hospital,
        String notes
) {
}
