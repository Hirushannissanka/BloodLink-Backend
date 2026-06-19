package com.v1.patient_request.service.impl;

import com.v1.patient_request.dto.request.PatientRequest;
import com.v1.patient_request.dto.response.PatientProfileResponse;
import com.v1.patient_request.entity.PatientProfile;
import com.v1.patient_request.exception.ResourceNotFoundException;
import com.v1.patient_request.repository.PatientProfileRepository;
import com.v1.patient_request.service.PatientProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// SRP: handles only patient profile business logic
// DIP: depends on PatientProfileService interface, not concrete classes
@Service
public class PatientProfileServiceImpl implements PatientProfileService {

    // Repository Pattern: data access abstracted via Spring Data JPA
    @Autowired
    private PatientProfileRepository patientProfileRepository;

    // Mapper Pattern: ModelMapper handles DTO ↔ Entity conversion
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientProfileResponse createProfile(Long userId, PatientRequest request) {
        if (patientProfileRepository.existsByUserId(userId)) {
            throw new IllegalStateException("Patient profile already exists for this user");
        }

        PatientProfile profile = modelMapper.map(request, PatientProfile.class);
        profile.setUserId(userId);

        PatientProfile saved = patientProfileRepository.save(profile);
        return modelMapper.map(saved, PatientProfileResponse.class);
    }

    @Override
    public PatientProfileResponse getProfile(Long userId) {
        PatientProfile profile = patientProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient profile not found for userId: " + userId));

        return modelMapper.map(profile, PatientProfileResponse.class);
    }

    @Override
    public PatientProfileResponse updateProfile(Long userId, PatientRequest request) {
        PatientProfile profile = patientProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient profile not found for userId: " + userId));

        // Update fields manually to avoid overwriting id/userId/createdAt
        profile.setBloodType(request.getBloodType());
        profile.setCity(request.getCity());
        profile.setDistrict(request.getDistrict());
        profile.setEmergencyContact(request.getEmergencyContact());
        profile.setMedicalNotes(request.getMedicalNotes());

        PatientProfile updated = patientProfileRepository.save(profile);
        return modelMapper.map(updated, PatientProfileResponse.class);
    }
}
