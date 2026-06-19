package com.v1.patient_request.service;

import com.v1.patient_request.dto.request.BloodRequestRequest;
import com.v1.patient_request.dto.response.BloodRequestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// ISP: BloodRequestService handles only blood request operations (Interface Segregation Principle)
public interface BloodRequestService {

    BloodRequestResponse createRequest(Long userId, BloodRequestRequest request);

    BloodRequestResponse getRequestById(Long userId, Long requestId);

    List<BloodRequestResponse> getActiveRequests(Long userId);

    Page<BloodRequestResponse> getRequestHistory(Long userId, Pageable pageable);

    BloodRequestResponse cancelRequest(Long userId, Long requestId);
}
