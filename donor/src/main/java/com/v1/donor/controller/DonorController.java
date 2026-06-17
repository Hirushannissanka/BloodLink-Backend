package com.v1.donor.controller;

import com.v1.donor.dto.request.DonorRequest;
import com.v1.donor.dto.response.DonorResponse;
import com.v1.donor.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donar")
public class DonorController {
    @Autowired
    private DonorService donorService;

    @PostMapping("/add")
    public DonorResponse createDonar(@RequestBody DonorRequest donorRequest){
        donorService.createDonor(donorRequest);
        return null;
    }
}
