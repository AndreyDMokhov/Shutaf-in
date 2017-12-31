package com.shutafin.model.exception.exceptions;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;

public class IncorrectPasswordException extends AbstractAPIException {

    @Override
    public ErrorType getErrorType() {
        return ErrorType.INCORRECT_PASSWORD_ERROR;
    }
}
