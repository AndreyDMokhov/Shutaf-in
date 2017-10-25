package com.shutafin.model.exceptions;


import com.shutafin.model.error.ErrorResponse;
import com.shutafin.model.error.ErrorType;

public abstract class AbstractAPIException extends RuntimeException {

    private String systemMessage = "Message not provided";

    public AbstractAPIException(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public AbstractAPIException() {
    }

    public String getMessage() {
        return systemMessage;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(getMessage(), getErrorType());
    }

    public abstract ErrorType getErrorType();

}