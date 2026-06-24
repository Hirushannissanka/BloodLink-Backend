package com.bloodlink.request.repository;

import com.bloodlink.request.model.RequestDonorResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestDonorResponseRepository extends JpaRepository<RequestDonorResponse, Long> {
    Optional<RequestDonorResponse> findByRequestIdAndDonorId(Long requestId, Long donorId);
}
