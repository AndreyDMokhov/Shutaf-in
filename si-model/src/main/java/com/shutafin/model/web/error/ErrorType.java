package com.shutafin.model.web.error;


public enum ErrorType {

    INPUT(400, ErrorCode.INP),
    SYSTEM(500, ErrorCode.SYS),
    AUTHENTICATION(401, ErrorCode.AUT),
    EMAIL_SEND_ERROR(503, ErrorCode.ESE),
    EMAIL_CONFIGURATION_ERROR(500, ErrorCode.ECE),
    EMAIL_DUPLICATION_EXCEPTION(400, ErrorCode.EDE),
    RESOURCE_NOT_FOUND_ERROR(404, ErrorCode.RNF),
    INCORRECT_PASSWORD_ERROR(401, ErrorCode.IPE),
    ACCOUNT_NOT_CONFIRMED(403, ErrorCode.ANC),
    ACCOUNT_BLOCKED(403, ErrorCode.ABL),
    IMAGE_SIZE_EXCEEDS_LIMIT(400, ErrorCode.SEL);


    private Integer httpCode;
    private ErrorCode errorCodeType;

    ErrorType(Integer httpCode, ErrorCode errorCodeType) {
        this.httpCode = httpCode;
        this.errorCodeType = errorCodeType;
    }

    public String getErrorCodeType() {
        return errorCodeType.name();
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public static ErrorType getById(String errorCodeName) {
        if (errorCodeName == null) {
            throw new IllegalArgumentException("Error code cannot be null");
        }

        for (ErrorType errorType : values()) {
            if (errorType.getErrorCodeType().equals(errorCodeName)) {
                return errorType;
            }
        }

        throw new IllegalArgumentException(String.format("Error type with error code %s does not exist", errorCodeName));
    }
}


enum ErrorCode {
    INP, SYS, AUT, ESE, ECE, EDE, RNF, IPE, ANC, ABL, SEL;
}