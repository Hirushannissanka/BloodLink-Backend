package com.v1.patient_request.exception;

// Custom exception for 404 Not Found scenarios
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
