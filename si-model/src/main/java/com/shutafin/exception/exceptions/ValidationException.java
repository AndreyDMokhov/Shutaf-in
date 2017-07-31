package com.shutafin.exception.exceptions;


import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;

import java.util.List;

public abstract class ValidationException extends AbstractAPIException {


    public ValidationException(String systemMessage) {
        super(systemMessage);
    }

    public ValidationException() {
    }


    public abstract ErrorType getErrorType();

    @Override
    public InputValidationError getErrorResponse() {
        return new InputValidationError(getMessage(), getErrorType(), getFieldErrors());
    }

    protected abstract List<String> getFieldErrors();
}