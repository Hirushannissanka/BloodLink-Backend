package com.v1.patient_request.repository;

import com.v1.patient_request.entity.BloodRequest;
import com.v1.patient_request.entity.PatientProfile;
import com.v1.patient_request.entity.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository Pattern: abstracts DB access for BloodRequest
@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    // Find active (PENDING or MATCHED) requests for a patient
    List<BloodRequest> findByPatientAndStatusIn(PatientProfile patient, List<RequestStatus> statuses);

    // Paginated full history for a patient
    Page<BloodRequest> findByPatient(PatientProfile patient, Pageable pageable);
}
