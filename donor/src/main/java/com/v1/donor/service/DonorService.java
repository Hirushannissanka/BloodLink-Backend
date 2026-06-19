package com.v1.donor.service;

import com.v1.donor.dto.request.DonorRequest;
import com.v1.donor.dto.response.DonorResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DonorService {
    public DonorResponse createDonor( DonorRequest donorRequest);

    public List<DonorResponse> getAllDonors();
    public DonorResponse getDonorById(String id);
    public DonorResponse updateDonor(String id, DonorRequest donorRequest);
    public void deleteDonor(String id);
}

