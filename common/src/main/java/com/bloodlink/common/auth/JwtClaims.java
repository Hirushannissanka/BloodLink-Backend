package com.bloodlink.common.auth;

import com.bloodlink.common.model.UserRole;

public record JwtClaims(Long userId, String email, UserRole role) {
}
