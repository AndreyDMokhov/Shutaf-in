package com.shutafin.model.exception.exceptions;

import com.shutafin.model.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;


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
