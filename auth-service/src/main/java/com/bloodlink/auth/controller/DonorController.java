package com.bloodlink.auth.controller;

import com.bloodlink.auth.dto.RegisterRequest;
import com.bloodlink.auth.service.UserAccountService;
import com.bloodlink.common.dto.UserSummary;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DonorController {
    private final UserAccountService service;

    public DonorController(UserAccountService service) {
        this.service = service;
    }

    @GetMapping("/api/donors/me")
    public UserSummary profile(@RequestHeader("Authorization") String authorization) {
        return service.donorProfile(authorization);
    }

    @PutMapping("/api/donors/me")
    public UserSummary updateProfile(@RequestHeader("Authorization") String authorization,
                                     @RequestBody RegisterRequest request) {
        return service.updateDonorProfile(authorization, request);
    }

    @GetMapping("/api/donors/me/donations")
    public List<Object> donationHistory() {
        return List.of();
    }

    @GetMapping("/internal/donors/match")
    public List<UserSummary> matchingDonors(
            @RequestParam("bloodType") String bloodType,
            @RequestParam(value = "district", required = false) String district) {
        return service.findMatchingDonors(bloodType, district);
    }

    @PostMapping("/internal/donors/{donorId}/reward")
    public void reward(@PathVariable("donorId") Long donorId, @RequestParam(name = "points", defaultValue = "10") int points) {
        service.addRewardPoints(donorId, points);
    }
}
