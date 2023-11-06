package com.jungdo.trainstationservice.exception;

import com.jungdo.trainstationservice.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse<Map<String, String>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        ErrorResponse<Map<String, String>> errorResponse = new ErrorResponse<>();
        Map<String, String> errors = new HashMap<>();

        errors.put("error_message", ex.getMessage());

        errorResponse.setError(errors);
        errorResponse.setMessage("Can not find the resource.");

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse<Map<String, String>> handleBadRequestException(
            BadRequestException ex) {
        ErrorResponse<Map<String, String>> errorResponse = new ErrorResponse<>();
        Map<String, String> errors = new HashMap<>();

        errors.put("error_message", ex.getMessage());

        errorResponse.setError(errors);
        errorResponse.setMessage("Bad request.");

        return errorResponse;
    }
}
