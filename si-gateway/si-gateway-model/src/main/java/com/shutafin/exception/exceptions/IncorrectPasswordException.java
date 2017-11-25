package com.shutafin.exception.exceptions;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;

public class IncorrectPasswordException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.INCORRECT_PASSWORD_ERROR;
    }
}
