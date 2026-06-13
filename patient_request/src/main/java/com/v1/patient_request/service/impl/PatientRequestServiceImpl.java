package com.v1.patient_request.service.impl;

import com.v1.patient_request.dto.request.BloodRequestRequest;
import com.v1.patient_request.dto.response.BloodRequestResponse;
import com.v1.patient_request.entity.BloodRequest;
import com.v1.patient_request.entity.PatientProfile;
import com.v1.patient_request.entity.RequestStatus;
import com.v1.patient_request.exception.ResourceNotFoundException;
import com.v1.patient_request.exception.UnauthorizedException;
import com.v1.patient_request.repository.BloodRequestRepository;
import com.v1.patient_request.repository.PatientProfileRepository;
import com.v1.patient_request.service.BloodRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// SRP: handles only blood request business logic
// DIP: depends on BloodRequestService interface (Dependency Inversion Principle)
@Service
public class PatientRequestServiceImpl implements BloodRequestService {

    // Repository Pattern: abstracts DB access
    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private PatientProfileRepository patientProfileRepository;

    // Mapper Pattern: ModelMapper handles DTO ↔ Entity conversion
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BloodRequestResponse createRequest(Long userId, BloodRequestRequest request) {
        PatientProfile patient = getPatientProfileByUserId(userId);

        BloodRequest bloodRequest = modelMapper.map(request, BloodRequest.class);
        bloodRequest.setPatient(patient);
        bloodRequest.setStatus(RequestStatus.PENDING);

        BloodRequest saved = bloodRequestRepository.save(bloodRequest);
        return toResponse(saved);
    }

    @Override
    public BloodRequestResponse getRequestById(Long userId, Long requestId) {
        BloodRequest bloodRequest = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Blood request not found with id: " + requestId));

        // Ownership check: ensure the request belongs to the calling user
        validateOwnership(userId, bloodRequest);
        return toResponse(bloodRequest);
    }

    @Override
    public List<BloodRequestResponse> getActiveRequests(Long userId) {
        PatientProfile patient = getPatientProfileByUserId(userId);

        List<RequestStatus> activeStatuses = List.of(RequestStatus.PENDING, RequestStatus.MATCHED);
        return bloodRequestRepository.findByPatientAndStatusIn(patient, activeStatuses)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BloodRequestResponse> getRequestHistory(Long userId, Pageable pageable) {
        PatientProfile patient = getPatientProfileByUserId(userId);
        return bloodRequestRepository.findByPatient(patient, pageable)
                .map(this::toResponse);
    }

    @Override
    public BloodRequestResponse cancelRequest(Long userId, Long requestId) {
        BloodRequest bloodRequest = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Blood request not found with id: " + requestId));

        validateOwnership(userId, bloodRequest);

        if (bloodRequest.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException(
                    "Only PENDING requests can be cancelled. Current status: " + bloodRequest.getStatus());
        }

        bloodRequest.setStatus(RequestStatus.CANCELLED);
        BloodRequest updated = bloodRequestRepository.save(bloodRequest);
        return toResponse(updated);
    }

    // Helper: get patient profile or throw 404
    private PatientProfile getPatientProfileByUserId(Long userId) {
        return patientProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient profile not found. Please create your profile first."));
    }

    // Helper: guard against cross-user access (returns 403)
    private void validateOwnership(Long userId, BloodRequest bloodRequest) {
        if (!bloodRequest.getPatient().getUserId().equals(userId)) {
            throw new UnauthorizedException("You do not have permission to access this request");
        }
    }

    // Helper: map BloodRequest entity to response DTO, including patientId
    private BloodRequestResponse toResponse(BloodRequest entity) {
        BloodRequestResponse response = modelMapper.map(entity, BloodRequestResponse.class);
        response.setPatientId(entity.getPatient().getId());
        return response;
    }
}

