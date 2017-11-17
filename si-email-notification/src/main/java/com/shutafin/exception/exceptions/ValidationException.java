package com.shutafin.exception.exceptions;

import com.shutafin.entity.error.ErrorType;
import com.shutafin.entity.web.error.InputValidationError;
import com.shutafin.exception.AbstractAPIException;

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