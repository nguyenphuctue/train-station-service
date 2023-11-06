package com.jungdo.trainstationservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> {
    private boolean success = false;
    private String message;
    private T error;
}

