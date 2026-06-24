package com.bloodlink.request.dto;

import com.bloodlink.common.model.RequestStatus;

import java.time.Instant;

public record BloodRequestResponse(
        Long id,
        Long requesterId,
        String patientName,
        String contactPhone,
        String district,
        String bloodTypeNeeded,
        String bloodType,
        Integer unitsNeeded,
        Integer units,
        String urgencyLevel,
        String urgency,
        String hospitalName,
        String hospital,
        RequestStatus status,
        String notes,
        Instant createdAt
) {
}
