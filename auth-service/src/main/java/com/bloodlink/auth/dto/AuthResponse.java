package com.bloodlink.auth.dto;

import com.bloodlink.common.dto.UserSummary;

public record AuthResponse(String token, UserSummary user) {
}
