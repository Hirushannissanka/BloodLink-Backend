package com.v1.patient_request.controller;

import com.v1.patient_request.dto.request.BloodRequestRequest;
import com.v1.patient_request.dto.response.BloodRequestResponse;
import com.v1.patient_request.service.BloodRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// SRP: handles only HTTP concerns for blood requests — delegates to BloodRequestService
@RestController
@RequestMapping("/patient/request")
public class PatientRequestController {

    // DIP: depends on BloodRequestService interface, not implementation
    @Autowired
    private BloodRequestService bloodRequestService;


    //POST /patient/request
    //Creates a new blood request with PENDING status.
     
    @PostMapping
    public ResponseEntity<BloodRequestResponse> createRequest(
            @Valid @RequestBody BloodRequestRequest request,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        BloodRequestResponse response = bloodRequestService.createRequest(userId, request);
        return ResponseEntity.status(201).body(response);
    }

    //GET /patient/request/{id}
    //Tracks a specific blood request by ID (must belong to the calling user).
     
    @GetMapping("/{id}")
    public ResponseEntity<BloodRequestResponse> getRequestById(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        return ResponseEntity.ok(bloodRequestService.getRequestById(userId, id));
    }

    //GET /patient/request/active
    //Returns all PENDING and MATCHED requests for the authenticated user.
     
    @GetMapping("/active")
    public ResponseEntity<List<BloodRequestResponse>> getActiveRequests(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        return ResponseEntity.ok(bloodRequestService.getActiveRequests(userId));
    }

    //GET /patient/request/history?page=0&size=10
    //Paginated history of all requests for the authenticated user.
    
    @GetMapping("/history")
    public ResponseEntity<Page<BloodRequestResponse>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(bloodRequestService.getRequestHistory(userId, pageable));
    }

    //PUT /patient/request/{id}/cancel
    //Cancels a PENDING blood request (sets status to CANCELLED).
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BloodRequestResponse> cancelRequest(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        return ResponseEntity.ok(bloodRequestService.cancelRequest(userId, id));
    }
}

