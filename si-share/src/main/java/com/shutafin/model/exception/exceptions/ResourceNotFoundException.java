package com.shutafin.model.exception.exceptions;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

public class ResourceNotFoundException extends AbstractAPIException {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.RESOURCE_NOT_FOUND_ERROR;
    }

    public ResourceNotFoundException(String systemMessage) {
        super(systemMessage);
    }

    public ResourceNotFoundException() {
    }
}
