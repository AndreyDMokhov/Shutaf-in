package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

/**
 * Created by evgeny on 7/11/2017.
 */
public class ResourceNotFoundException extends AbstractAPIException {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.RESOURCE_NOT_FOUND_ERROR;
    }
}
