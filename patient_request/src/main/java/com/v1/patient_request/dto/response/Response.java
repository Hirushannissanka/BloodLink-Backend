package com.v1.patient_request.dto.response;

import lombok.Data;

@Data
public class Response {
    private String responseCode;
    private String status;
    private String message;
}
