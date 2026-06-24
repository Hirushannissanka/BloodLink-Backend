package com.bloodlink.auth.controller;

import com.bloodlink.auth.dto.PatientProfileRequest;
import com.bloodlink.auth.model.PatientProfile;
import com.bloodlink.auth.service.UserAccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final UserAccountService service;

    public PatientController(UserAccountService service) {
        this.service = service;
    }

    @PostMapping("/profile")
    public PatientProfile createProfile(@RequestHeader("Authorization") String authorization,
                                        @RequestBody PatientProfileRequest request) {
        return service.createPatientProfile(authorization, request);
    }
}
