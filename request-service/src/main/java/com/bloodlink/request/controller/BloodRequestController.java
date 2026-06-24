package com.bloodlink.request.controller;

import com.bloodlink.request.dto.BloodRequestCreateRequest;
import com.bloodlink.request.dto.BloodRequestResponse;
import com.bloodlink.request.dto.DonorDecisionRequest;
import com.bloodlink.request.service.BloodRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class BloodRequestController {
    private final BloodRequestService service;

    public BloodRequestController(BloodRequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<BloodRequestResponse> list(@RequestHeader(value = "Authorization", required = false) String authorization,
                                           @RequestParam(name = "bloodType", required = false) String bloodType,
                                           @RequestParam(name = "district", required = false) String district,
                                           @RequestParam(name = "status", required = false) String status) {
        return service.list(authorization, bloodType, district, status);
    }

    @GetMapping("/{id}")
    public BloodRequestResponse get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping
    public BloodRequestResponse create(@RequestHeader("Authorization") String authorization,
                                       @Valid @RequestBody BloodRequestCreateRequest request) {
        return service.create(authorization, request);
    }

    @GetMapping("/active")
    public List<BloodRequestResponse> active(@RequestHeader("Authorization") String authorization) {
        return service.activeForRequester(authorization);
    }

    @GetMapping("/history")
    public List<BloodRequestResponse> history(@RequestHeader("Authorization") String authorization) {
        return service.historyForRequester(authorization);
    }

    @PutMapping("/{id}/cancel")
    public BloodRequestResponse cancel(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        return service.cancel(authorization, id);
    }

    @PostMapping("/{id}/respond")
    public BloodRequestResponse respond(@RequestHeader("Authorization") String authorization,
                                        @PathVariable("id") Long id,
                                        @Valid @RequestBody DonorDecisionRequest request) {
        return service.respond(authorization, id, request);
    }
}
