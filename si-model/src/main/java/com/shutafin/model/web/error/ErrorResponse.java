package com.shutafin.model.web.error;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorResponse {

    private String errorMessage;

    @JsonIgnore
    private ErrorType errorType;


    public ErrorResponse(String errorMessage, ErrorType errorType) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
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
}
