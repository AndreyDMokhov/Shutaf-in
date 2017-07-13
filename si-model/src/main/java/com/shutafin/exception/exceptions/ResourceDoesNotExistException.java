package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

public class ResourceDoesNotExistException extends AbstractAPIException {

    public ResourceDoesNotExistException(String systemMessage) {
        super(systemMessage);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.RESOURCE_DOES_NOT_EXIST;
    }
}
