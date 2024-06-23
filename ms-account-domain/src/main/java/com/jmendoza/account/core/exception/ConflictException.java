package com.jmendoza.account.core.exception;

import com.jmendoza.account.core.enums.ResponseDictionary;
import org.springframework.http.HttpStatus;

public class ConflictException extends CustomException {
    public ConflictException(ResponseDictionary responseDictionary, Object... args) {
        super(HttpStatus.CONFLICT.value(), responseDictionary.getCode(), responseDictionary.getMessage(), args);
    }
}
