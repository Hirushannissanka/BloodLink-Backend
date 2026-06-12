package com.v1.auth.dto.response;

import lombok.Data;

@Data
public class Response {
    private String responseCode;
    private String status;
    private String message;

}
