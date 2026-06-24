package com.v1.auth.controller;

import com.v1.auth.dto.request.AuthRequest;
import com.v1.auth.dto.response.AuthResponse;
import com.v1.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signUp(@RequestBody AuthRequest authRequest){
        return authService.signUp(authRequest);

    }

}
