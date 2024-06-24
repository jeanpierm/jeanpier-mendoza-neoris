package com.jmendoza.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmendoza.account.core.enums.ResponseDictionary;
import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse<T> {
    private int httpStatus;
    private String code;
    private String message;
    private T data;

    public ApiResponse(int httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), ResponseDictionary.OK.getCode(), ResponseDictionary.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> ok() {
        return ok(null);
    }

    @JsonIgnore
    public boolean isOk() {
        return ResponseDictionary.OK.getCode().equals(code);
    }
}
