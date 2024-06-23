package com.jmendoza.customer.core.exception;

import com.jmendoza.customer.core.enums.ResponseDictionary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(ResponseDictionary responseDictionary, Object... args) {
        super(HttpStatus.NOT_FOUND.value(), responseDictionary.getCode(), responseDictionary.getMessage(), args);
    }
}