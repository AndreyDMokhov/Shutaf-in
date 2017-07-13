package com.shutafin.model.web.error;


public enum ErrorType {

    INPUT(400, "INP"),
    SYSTEM(500, "SYS"),
    AUTHENTICATION(401, "AUT"),
    EMAIL_SEND_ERROR(503, "ESE"),
    EMAIL_CONFIGURATION_ERROR(500, "ECE"),
    RESOURCE_DOES_NOT_EXIST(500, "RNE");

    private Integer httpCode;
    private String errorCodeType;

    ErrorType(Integer httpCode, String errorCodeType) {
        this.httpCode = httpCode;
        this.errorCodeType = errorCodeType;
    }

    public String getErrorCodeType() {
        return errorCodeType;
    }

    public Integer getHttpCode() {
        return httpCode;
    }
}
