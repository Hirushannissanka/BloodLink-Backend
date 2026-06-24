package com.bloodlink.auth.dto;

import com.bloodlink.common.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String phone,
        Integer age,
        String district,
        String nearestHospital,
        String bloodType,
        @Size(min = 8) String password,
        @NotNull UserRole role,
        String emergencyContact
) {
}
