package com.v1.donor.controller;

import com.v1.donor.dto.request.DonorRequest;
import com.v1.donor.dto.response.DonorResponse;
import com.v1.donor.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/get")
    public DonorResponse getDonorById(@RequestParam String id){
        return donorService.getDonorById(id);
    }

    @GetMapping("/get-all")
    public java.util.List<DonorResponse> getAllDonors() {
        return donorService.getAllDonors();
    }

    @PutMapping("/update")
    public DonorResponse updateDonor(@RequestParam String id, @RequestBody DonorRequest donorRequest) {
        return donorService.updateDonor(id, donorRequest);
    }

    @DeleteMapping("/delete")
    public void deleteDonor(@RequestParam String id) {
        donorService.deleteDonor(id);
    }
}
