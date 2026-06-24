package com.bloodlink.request.dto;

import jakarta.validation.constraints.NotBlank;

public record DonorDecisionRequest(@NotBlank String action) {
}
