package com.jmendoza.account.core.exception;

import com.jmendoza.account.core.enums.ResponseDictionary;
import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(ResponseDictionary responseDictionary, Object... args) {
        super(HttpStatus.BAD_REQUEST.value(), responseDictionary.getCode(), responseDictionary.getMessage(), args);
    }
}