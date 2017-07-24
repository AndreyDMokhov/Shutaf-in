package com.shutafin.exception.exceptions;


import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ValidationException extends AbstractAPIException {

    private static final String DOT_SEPARATOR = ".";

    public ValidationException(String systemMessage) {
        super(systemMessage);
    }

    public ValidationException() {
    }


    public abstract ErrorType getErrorType();

    @Override
    public InputValidationError getErrorResponse() {
        return new InputValidationError(getMessage(), getErrorType(), getViolatedConstraintsList());
    }

    private List<String> getViolatedConstraintsList() {
        List<String> violatedConstraints = new ArrayList<>();
        for (Map.Entry<String, String> map : getFieldErrors().entrySet()) {
            String builder =
                    getErrorType().getErrorCodeType() +
                            DOT_SEPARATOR +
                            map.getKey() +
                            DOT_SEPARATOR +
                            map.getValue();

            violatedConstraints.add(builder);
        }
        return violatedConstraints;
    }

    protected abstract Map<String, String> getFieldErrors();
}