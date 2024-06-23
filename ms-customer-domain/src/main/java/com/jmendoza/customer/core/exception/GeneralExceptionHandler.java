package com.jmendoza.customer.core.exception;

import com.jmendoza.customer.core.enums.ResponseDictionary;
import com.jmendoza.customer.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        var body = new ApiResponse<Void>(e.getHttpStatus(), e.getCode(), e.getMessage(), null);
        log.error("Custom Exception: {}", e.getMessage());
        return new ResponseEntity<>(body, HttpStatusCode.valueOf(e.getHttpStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        var status = HttpStatus.BAD_REQUEST;
        var body = new ApiResponse<Void>(
                status.value(),
                ResponseDictionary.INVALID_REQUEST.getCode(),
                ResponseDictionary.INVALID_REQUEST.getMessage() + ": " + errors
        );
        log.error("Invalid Request: {}", errors);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var body = new ApiResponse<Void>(
                status.value(),
                ResponseDictionary.INTERNAL_SERVER_ERROR.getCode(),
                ResponseDictionary.INTERNAL_SERVER_ERROR.getMessage()
        );
        log.error("Internal Server Error: {}", e.getMessage(), e);
        return new ResponseEntity<>(body, status);
    }
}
