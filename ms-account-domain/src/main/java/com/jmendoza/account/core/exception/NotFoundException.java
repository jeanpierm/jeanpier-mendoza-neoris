package com.jmendoza.account.core.exception;

import com.jmendoza.account.core.enums.ResponseDictionary;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(ResponseDictionary responseDictionary, Object... args) {
        super(HttpStatus.NOT_FOUND.value(), responseDictionary.getCode(), responseDictionary.getMessage(), args);
    }
}