package com.shutafin.model.web.error;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorResponse {

    private String errorMessage;
    private String errorTypeCode;

    @JsonIgnore
    private ErrorType errorType;


    public ErrorResponse(String errorMessage, ErrorType errorType) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.errorTypeCode = this.errorType.getErrorCodeType();
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorTypeCode() {
        return errorTypeCode;
    }

    public void setErrorTypeCode(String errorTypeCode) {
        this.errorTypeCode = errorTypeCode;
    }
}
