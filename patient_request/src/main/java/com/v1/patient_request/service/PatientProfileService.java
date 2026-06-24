package com.v1.patient_request.service;

import com.v1.patient_request.dto.request.PatientRequest;
import com.v1.patient_request.dto.response.PatientProfileResponse;

// ISP: PatientProfileService handles only patient profile operations (Interface Segregation Principle)
public interface PatientProfileService {

    PatientProfileResponse createProfile(Long userId, PatientRequest request);

    PatientProfileResponse getProfile(Long userId);

    PatientProfileResponse updateProfile(Long userId, PatientRequest request);
}
