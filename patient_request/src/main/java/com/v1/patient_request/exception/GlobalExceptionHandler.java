package com.v1.patient_request.exception;

import com.v1.patient_request.dto.response.Response;
import com.v1.patient_request.utils.ResponseCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

// Chain of Responsibility Pattern: centralized exception → HTTP response mapping (@ControllerAdvice)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> handleNotFound(ResourceNotFoundException ex) {
        Response response = new Response();
        response.setResponseCode(ResponseCodes.NOT_FOUND);
        response.setStatus("FAILED");
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response> handleUnauthorized(UnauthorizedException ex) {
        Response response = new Response();
        response.setResponseCode(ResponseCodes.UNAUTHORIZED);
        response.setStatus("FAILED");
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Response> handleConflict(IllegalStateException ex) {
        Response response = new Response();
        response.setResponseCode(ResponseCodes.CONFLICT);
        response.setStatus("FAILED");
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Bean Validation errors (@Valid on request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        Response response = new Response();
        response.setResponseCode(ResponseCodes.BAD_REQUEST);
        response.setStatus("FAILED");
        response.setMessage(errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGeneral(Exception ex) {
        Response response = new Response();
        response.setResponseCode(ResponseCodes.INTERNAL_ERROR);
        response.setStatus("FAILED");
        response.setMessage("An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
