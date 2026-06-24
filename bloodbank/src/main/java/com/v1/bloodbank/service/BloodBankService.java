package com.v1.bloodbank.service;

import com.v1.bloodbank.dto.request.BloodBankRequest;
import com.v1.bloodbank.dto.response.BloodBankResponse;

import java.util.List;

public interface BloodBankService {
    BloodBankResponse registerBloodBank(BloodBankRequest request);
    List<BloodBankResponse> getAllBloodBanks();
    List<BloodBankResponse> searchBloodBanks(String location, String bloodGroup);
    BloodBankResponse getBloodBankById(Long id);
    BloodBankResponse updateBloodBank(Long id, BloodBankRequest request);
    void deleteBloodBank(Long id);
}
