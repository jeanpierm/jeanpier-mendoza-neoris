package com.jmendoza.customer.core.enums;

import lombok.Getter;

@Getter
public enum ResponseDictionary {

    OK("0", "Operación exitosa"),
    INTERNAL_SERVER_ERROR("F0-999", "Error interno del servidor. Por favor, inténtelo de nuevo más tarde"),
    INVALID_REQUEST("F0-000", "Solicitud inválida"),
    CUSTOMER_NOT_FOUND("F1-001", "Cliente con identificaicon %s no encontrado"),
    CUSTOMER_ALREADY_EXISTS("F1-002", "Cliente con identificación %s ya existe");

    private final String code;
    private final String message;

    ResponseDictionary(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
