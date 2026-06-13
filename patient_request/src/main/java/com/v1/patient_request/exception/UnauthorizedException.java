package com.v1.patient_request.exception;

// Custom exception for 401 / 403 scenarios
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
