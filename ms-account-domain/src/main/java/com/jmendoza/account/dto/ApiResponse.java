package com.jmendoza.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmendoza.account.core.enums.ResponseDictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private int httpStatus;
    private String code;
    private String message;
    private T data;

    /**
     * Constructor para crear un objeto ApiResponse a partir de un JSON.
     * Permite que Jackson funcione correctamente.
     * @param json JSON string
     */
    public ApiResponse(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ApiResponse<T> response = mapper.readValue(json, new TypeReference<ApiResponse<T>>() {});
        this.httpStatus = response.getHttpStatus();
        this.code = response.getCode();
        this.message = response.getMessage();
        this.data = response.getData();
    }

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
