package com.bloodlink.request.repository;

import com.bloodlink.common.model.BloodType;
import com.bloodlink.common.model.RequestStatus;
import com.bloodlink.request.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findByBloodTypeNeededAndStatusIn(BloodType bloodType, List<RequestStatus> statuses);
    List<BloodRequest> findByRequesterIdOrderByCreatedAtDesc(Long requesterId);
    List<BloodRequest> findByStatusInOrderByCreatedAtDesc(List<RequestStatus> statuses);
    List<BloodRequest> findByDistrictIgnoreCaseAndStatusInOrderByCreatedAtDesc(String district, List<RequestStatus> statuses);
}
