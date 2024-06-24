package com.jmendoza.customer.core.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomException extends RuntimeException {
    private int httpStatus;
    private String code;
    private String message;

    public CustomException(int httpStatus, String code, String message, Object... args) {
        this.httpStatus = httpStatus;
        this.message = String.format(message, args);
        this.code = code;
    }
}
