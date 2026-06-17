package com.v1.donor.service;

import com.v1.donor.dto.request.DonorRequest;
import com.v1.donor.dto.response.DonorResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface DonorService {
    public DonorResponse createDonor( DonorRequest donorRequest);
    public DonorResponse getAllDonors();
}
