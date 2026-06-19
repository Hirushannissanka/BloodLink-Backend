package com.v1.patient_request.dto.response;

import com.v1.patient_request.entity.BloodType;
import com.v1.patient_request.entity.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

// DTO Pattern: decouples BloodRequest entity from API response
@Data
public class BloodRequestResponse {
    private Long id;
    private Long patientId;
    private BloodType bloodTypeNeeded;
    private Integer unitsNeeded;
    private String urgencyLevel;
    private String hospitalName;
    private String hospitalAddress;
    private String city;
    private String district;
    private RequestStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
