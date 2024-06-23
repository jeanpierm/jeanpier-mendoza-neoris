package com.jmendoza.account.core.enums;

import lombok.Getter;

@Getter
public enum ResponseDictionary {

    OK("0", "Operación exitosa"),
    INTERNAL_SERVER_ERROR("F0-999", "Error interno del servidor. Por favor, inténtelo de nuevo más tarde"),
    INVALID_REQUEST("F0-000", "Solicitud inválida"),
    ACCOUNT_NOT_FOUND("F1-100", "Cuenta %s no encontrada"),
    CUSTOMER_SERVICE_BAD_GATEWAY("F1-101", "Microservicio de clientes no disponible"),
    TRANSACTION_CANNOT_BE_ZERO("F1-102", "El valor del movimiento no puede ser cero"),
    TRANSACTION_NOT_FOUND("F1-103", "Movimiento %s no encontrado"),
    INSUFFICIENT_BALANCE("F3-100", "Saldo no disponible");

    private final String code;
    private final String message;

    ResponseDictionary(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
