package com.v1.bloodbank.service.impl;

import com.v1.bloodbank.dto.request.BloodBankRequest;
import com.v1.bloodbank.dto.response.BloodBankResponse;
import com.v1.bloodbank.entity.BloodBank;
import com.v1.bloodbank.repository.BloodBankRepository;
import com.v1.bloodbank.service.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BloodBankServiceImpl implements BloodBankService {

    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Override
    public BloodBankResponse registerBloodBank(BloodBankRequest request) {
        BloodBank bloodBank = new BloodBank();
        bloodBank.setName(request.getName());
        bloodBank.setAddress(request.getAddress());
        bloodBank.setCity(request.getCity());
        bloodBank.setPhoneNumber(request.getPhoneNumber());
        bloodBank.setEmail(request.getEmail());

        BloodBank savedBank = bloodBankRepository.save(bloodBank);
        return mapToResponse(savedBank);
    }

    @Override
    public List<BloodBankResponse> getAllBloodBanks() {
        return bloodBankRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BloodBankResponse> searchBloodBanks(String location, String bloodGroup) {
        // For now, search by city. Can be enhanced later with proper filtering.
        return bloodBankRepository.findByCity(location).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BloodBankResponse getBloodBankById(Long id) {
        Optional<BloodBank> bloodBankOptional = bloodBankRepository.findById(id);
        if (bloodBankOptional.isPresent()) {
            return mapToResponse(bloodBankOptional.get());
        }
        // Handle not found case appropriately, e.g., throw exception
        throw new RuntimeException("Blood Bank not found with id: " + id);
    }

    @Override
    public BloodBankResponse updateBloodBank(Long id, BloodBankRequest request) {
        Optional<BloodBank> bloodBankOptional = bloodBankRepository.findById(id);
        if (bloodBankOptional.isPresent()) {
            BloodBank bloodBank = bloodBankOptional.get();
            bloodBank.setName(request.getName());
            bloodBank.setAddress(request.getAddress());
            bloodBank.setCity(request.getCity());
            bloodBank.setPhoneNumber(request.getPhoneNumber());
            bloodBank.setEmail(request.getEmail());

            BloodBank updatedBank = bloodBankRepository.save(bloodBank);
            return mapToResponse(updatedBank);
        }
        // Handle not found case
        throw new RuntimeException("Blood Bank not found with id: " + id);
    }

    @Override
    public void deleteBloodBank(Long id) {
        if (!bloodBankRepository.existsById(id)) {
            throw new RuntimeException("Blood Bank not found with id: " + id);
        }
        bloodBankRepository.deleteById(id);
    }

    private BloodBankResponse mapToResponse(BloodBank bloodBank) {
        BloodBankResponse response = new BloodBankResponse();
        response.setBloodBankId(bloodBank.getBloodBankId());
        response.setName(bloodBank.getName());
        response.setAddress(bloodBank.getAddress());
        response.setCity(bloodBank.getCity());
        response.setPhoneNumber(bloodBank.getPhoneNumber());
        response.setEmail(bloodBank.getEmail());
        return response;
    }
}
