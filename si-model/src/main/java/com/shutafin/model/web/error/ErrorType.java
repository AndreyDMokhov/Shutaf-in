package com.shutafin.model.web.error;


public enum ErrorType {

<<<<<<< HEAD
    INPUT(400, "INP"),
    SYSTEM(500, "SYS"),
    AUTHENTICATION(401, "AUT"),
    EMAIL_SEND_ERROR(503, "ESE"),
    EMAIL_CONFIGURATION_ERROR(500, "ECE"),
    RESOURCE_NOT_FOUND(404, "RNF");
=======
    INPUT(400, ErrorCode.INP),
    SYSTEM(500, ErrorCode.SYS),
    AUTHENTICATION(401, ErrorCode.AUT),
    EMAIL_SEND_ERROR(503, ErrorCode.ESE),
    EMAIL_CONFIGURATION_ERROR(500, ErrorCode.ECE),
    EMAIL_DUPLICATION_EXCEPTION(400, ErrorCode.EDE);
>>>>>>> develop

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
}


enum ErrorCode {
    INP, SYS, AUT, ESE, ECE, EDE;
}