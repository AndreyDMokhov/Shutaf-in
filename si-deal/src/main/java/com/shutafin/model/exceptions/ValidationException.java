package com.shutafin.model.exceptions;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.error.errors.InputValidationError;

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