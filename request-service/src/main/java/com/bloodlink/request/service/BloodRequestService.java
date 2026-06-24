package com.bloodlink.request.service;

import com.bloodlink.common.auth.JwtClaims;
import com.bloodlink.common.auth.JwtService;
import com.bloodlink.common.dto.UserSummary;
import com.bloodlink.common.model.*;
import com.bloodlink.request.dto.BloodRequestCreateRequest;
import com.bloodlink.request.dto.BloodRequestResponse;
import com.bloodlink.request.dto.DonorDecisionRequest;
import com.bloodlink.request.dto.NotificationRequest;
import com.bloodlink.request.model.BloodRequest;
import com.bloodlink.request.model.RequestDonorResponse;
import com.bloodlink.request.repository.BloodRequestRepository;
import com.bloodlink.request.repository.RequestDonorResponseRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class BloodRequestService {
    private final BloodRequestRepository requests;
    private final RequestDonorResponseRepository responses;
    private final JwtService jwtService;
    private final RestClient restClient;
    private final String authUrl;
    private final String notificationUrl;

    public BloodRequestService(BloodRequestRepository requests, RequestDonorResponseRepository responses,
                               JwtService jwtService, RestClient restClient,
                               @Value("${app.services.auth-url}") String authUrl,
                               @Value("${app.services.notification-url}") String notificationUrl) {
        this.requests = requests;
        this.responses = responses;
        this.jwtService = jwtService;
        this.restClient = restClient;
        this.authUrl = authUrl;
        this.notificationUrl = notificationUrl;
    }

    public List<BloodRequestResponse> list(String authorization, String bloodType, String district, String status) {
        if (bloodType != null && !bloodType.isBlank()) {
            return requests.findByBloodTypeNeededAndStatusIn(BloodType.fromClient(bloodType), activeStatuses())
                    .stream().map(this::response).toList();
        }
        if (district != null && !district.isBlank()) {
            return requests.findByDistrictIgnoreCaseAndStatusInOrderByCreatedAtDesc(district, activeStatuses())
                    .stream().map(this::response).toList();
        }
        return requests.findByStatusInOrderByCreatedAtDesc(activeStatuses()).stream().map(this::response).toList();
    }

    public BloodRequestResponse get(Long id) {
        return requests.findById(id).map(this::response).orElseThrow(() -> new IllegalArgumentException("Request not found."));
    }

    @Transactional
    public BloodRequestResponse create(String authorization, BloodRequestCreateRequest request) {
        JwtClaims claims = jwtService.parse(authorization);
        BloodRequest entity = new BloodRequest();
        entity.setRequesterId(claims.userId());
        entity.setPatientName(request.patientName());
        entity.setContactPhone(request.contactPhone());
        entity.setDistrict(request.district());
        entity.setBloodTypeNeeded(BloodType.fromClient(request.bloodType()));
        entity.setUnitsNeeded(request.units());
        entity.setUrgencyLevel(UrgencyLevel.fromClient(request.urgency()));
        entity.setHospitalName(request.hospital());
        entity.setNotes(request.notes());
        BloodRequest saved = requests.save(entity);
        notifyMatchingDonors(saved);
        notifyPatient(claims, saved);
        return response(saved);
    }

    public List<BloodRequestResponse> activeForRequester(String authorization) {
        JwtClaims claims = jwtService.parse(authorization);
        return requests.findByRequesterIdOrderByCreatedAtDesc(claims.userId()).stream()
                .filter(r -> activeStatuses().contains(r.getStatus()))
                .map(this::response)
                .toList();
    }

    public List<BloodRequestResponse> historyForRequester(String authorization) {
        JwtClaims claims = jwtService.parse(authorization);
        return requests.findByRequesterIdOrderByCreatedAtDesc(claims.userId()).stream().map(this::response).toList();
    }

    @Transactional
    public BloodRequestResponse cancel(String authorization, Long id) {
        JwtClaims claims = jwtService.parse(authorization);
        BloodRequest request = requests.findById(id).orElseThrow(() -> new IllegalArgumentException("Request not found."));
        if (!request.getRequesterId().equals(claims.userId())) {
            throw new IllegalArgumentException("You can cancel only your own request.");
        }
        request.setStatus(RequestStatus.CANCELLED);
        return response(requests.save(request));
    }

    @Transactional
    public BloodRequestResponse respond(String authorization, Long requestId, DonorDecisionRequest decision) {
        JwtClaims claims = jwtService.parse(authorization);
        BloodRequest request = requests.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Request not found."));
        RequestDonorResponse response = responses.findByRequestIdAndDonorId(requestId, claims.userId()).orElseGet(RequestDonorResponse::new);
        response.setRequestId(requestId);
        response.setDonorId(claims.userId());
        response.setStatus(DonorResponseStatus.valueOf(decision.action()));
        responses.save(response);

        if (response.getStatus() == DonorResponseStatus.ACCEPT) {
            request.setStatus(RequestStatus.MATCHED);
            restClient.post().uri(authUrl + "/internal/donors/{donorId}/reward?points=10", claims.userId()).retrieve().toBodilessEntity();
        }
        return response(requests.save(request));
    }

    private void notifyMatchingDonors(BloodRequest request) {
        // Strategy Pattern: matching logic can later swap district-only matching for distance-based matching.
        List<UserSummary> donors = restClient.get()
                .uri(authUrl + "/internal/donors/match?bloodType={bloodType}",
                        request.getBloodTypeNeeded().name())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        if (donors == null) {
            return;
        }
        donors.forEach(donor -> restClient.post()
                .uri(notificationUrl + "/internal/notifications")
                .body(new NotificationRequest(donor.id(), request.getId(), donor.email(), donor.phone(),
                        "Urgent blood request: Blood type " + request.getBloodTypeNeeded().toClient() + 
                        " is needed for patient " + request.getPatientName() + " at " + request.getHospitalName()))
                .retrieve()
                .toBodilessEntity());
    }

    private void notifyPatient(JwtClaims claims, BloodRequest request) {
        if (claims.email() != null && !claims.email().isBlank()) {
            restClient.post()
                    .uri(notificationUrl + "/internal/notifications")
                    .body(new NotificationRequest(claims.userId(), request.getId(), claims.email(), request.getContactPhone(),
                            "Your blood request for patient " + request.getPatientName() + 
                            " (" + request.getBloodTypeNeeded().toClient() + ") has been successfully registered. " +
                            "Matching donors have been notified."))
                    .retrieve()
                    .toBodilessEntity();
        }
    }

    private List<RequestStatus> activeStatuses() {
        return List.of(RequestStatus.PENDING, RequestStatus.MATCHED);
    }

    private BloodRequestResponse response(BloodRequest r) {
        return new BloodRequestResponse(r.getId(), r.getRequesterId(), r.getPatientName(), r.getContactPhone(),
                r.getDistrict(), r.getBloodTypeNeeded().name(), r.getBloodTypeNeeded().toClient(), r.getUnitsNeeded(),
                r.getUnitsNeeded(), r.getUrgencyLevel().name(), r.getUrgencyLevel().toClient(), r.getHospitalName(),
                r.getHospitalName(), r.getStatus(), r.getNotes(), r.getCreatedAt());
    }
}
