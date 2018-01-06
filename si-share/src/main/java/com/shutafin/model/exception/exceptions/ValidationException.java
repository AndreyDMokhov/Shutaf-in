package com.shutafin.model.exception.exceptions;

import com.shutafin.model.error.ErrorType;
import com.shutafin.model.error.errors.InputValidationError;
import com.shutafin.model.exception.AbstractAPIException;

import java.util.List;

public abstract class ValidationException extends AbstractAPIException {

    public ValidationException(String errorMessage) {
        super(errorMessage);
    }

    public ValidationException() {
    }

    public abstract ErrorType getErrorType();

    @Override
    public InputValidationError getErrorResponse() {
        return new InputValidationError(getErrorMessage(), getErrorType(), getErrors());
    }

    protected abstract List<String> getErrors();
}