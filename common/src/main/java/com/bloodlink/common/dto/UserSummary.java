package com.bloodlink.common.dto;

import com.bloodlink.common.model.UserRole;

public record UserSummary(
        Long id,
        String fullName,
        String email,
        String phone,
        Integer age,
        String district,
        String nearestHospital,
        String bloodType,
        UserRole role,
        Integer rewardPoints
) {
}
