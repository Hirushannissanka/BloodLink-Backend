package com.bloodlink.camp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CampRequest(
        @NotBlank String name,
        String organizer,
        @NotBlank String district,
        @NotBlank String location,
        @NotNull LocalDate date,
        String startTime,
        String endTime,
        String contactPhone,
        String contactEmail,
        String notes,
        Integer targetUnits,
        List<String> bloodTypesNeeded
) {
}
