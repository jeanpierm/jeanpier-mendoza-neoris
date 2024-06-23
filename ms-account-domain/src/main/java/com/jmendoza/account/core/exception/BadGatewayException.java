package com.jmendoza.account.core.exception;

import com.jmendoza.account.core.enums.ResponseDictionary;
import org.springframework.http.HttpStatus;

public class BadGatewayException extends CustomException {
    public BadGatewayException(ResponseDictionary responseDictionary, Object... args) {
        super(HttpStatus.BAD_GATEWAY.value(), responseDictionary.getCode(), responseDictionary.getMessage(), args);
    }
}