package com.bloodlink.camp.controller;

import com.bloodlink.camp.dto.CampRequest;
import com.bloodlink.camp.dto.CampResponse;
import com.bloodlink.camp.service.CampService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camps")
public class CampController {
    private final CampService service;

    public CampController(CampService service) {
        this.service = service;
    }

    @GetMapping
    public List<CampResponse> list(@RequestParam(name = "district", required = false) String district,
                                   @RequestParam(name = "upcoming", defaultValue = "false") boolean upcoming) {
        return service.list(district, upcoming);
    }

    @PostMapping
    public CampResponse create(@RequestHeader("Authorization") String authorization,
                               @Valid @RequestBody CampRequest request) {
        return service.create(authorization, request);
    }
}
