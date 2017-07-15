package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

public class ResourceNotFoundException extends AbstractAPIException {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.RESOURCE_NOT_FOUND;
    }
}
