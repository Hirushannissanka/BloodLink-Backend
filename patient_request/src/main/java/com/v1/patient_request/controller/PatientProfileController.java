package com.v1.patient_request.controller;

import com.v1.patient_request.dto.request.PatientRequest;
import com.v1.patient_request.dto.response.PatientProfileResponse;
import com.v1.patient_request.service.PatientProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// SRP: handles only HTTP concerns for patient profile — delegates to PatientProfileService
@RestController
@RequestMapping("/patient/profile")
public class PatientProfileController {

    // DIP: depends on PatientProfileService interface, not implementation
    @Autowired
    private PatientProfileService patientProfileService;

    /**
     * POST /patient/profile
     * Creates a patient profile for the authenticated user.
     */
    @PostMapping
    public ResponseEntity<PatientProfileResponse> createProfile(
            @Valid @RequestBody PatientRequest request,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        PatientProfileResponse response = patientProfileService.createProfile(userId, request);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * GET /patient/profile
     * Returns the profile of the authenticated user.
     */
    @GetMapping
    public ResponseEntity<PatientProfileResponse> getProfile(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        return ResponseEntity.ok(patientProfileService.getProfile(userId));
    }

    /**
     * PUT /patient/profile
     * Updates the authenticated user's profile.
     */
    @PutMapping
    public ResponseEntity<PatientProfileResponse> updateProfile(
            @Valid @RequestBody PatientRequest request,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        return ResponseEntity.ok(patientProfileService.updateProfile(userId, request));
    }
}
