package com.v1.auth.service;

import com.v1.auth.dto.request.AuthRequest;
import com.v1.auth.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(AuthRequest authRequest);
    AuthResponse login(AuthRequest authRequest);
}
