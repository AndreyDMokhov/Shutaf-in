package com.shutafin.exception.exceptions;

import com.shutafin.entity.error.ErrorType;
import com.shutafin.exception.AbstractAPIException;

public class EmailNotificationProcessingException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.EMAIL_CONFIGURATION_ERROR;
    }
}
