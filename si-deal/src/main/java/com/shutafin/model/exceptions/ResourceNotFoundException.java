package com.shutafin.model.exceptions;

import com.shutafin.model.error.ErrorType;

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
