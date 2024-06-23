package com.jmendoza.customer.core.exception;

import com.jmendoza.customer.core.enums.ResponseDictionary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ConflictException extends CustomException {
    public ConflictException(ResponseDictionary responseDictionary, Object... args) {
        super(HttpStatus.CONFLICT.value(), responseDictionary.getCode(), responseDictionary.getMessage(), args);
    }
}
