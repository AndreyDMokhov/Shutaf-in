package com.shutafin.exception.exceptions;


import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class InputValidationException extends AbstractAPIException {

    private static final String DOT_SEPARATOR = ".";
    private BindingResult result;

    public InputValidationException(BindingResult result) {
        this.result = result;
    }


    @Override
    protected ErrorType getErrorType() {
        return ErrorType.INPUT;
    }

    @Override
    public InputValidationError getErrorResponse() {
        return new InputValidationError(getMessage(), getErrorType(), getViolatedConstraintsList());
    }

    private List<String> getViolatedConstraintsList() {
        List<String> violatedConstraints = new ArrayList<>();
        for (FieldError fieldError : this.result.getFieldErrors()) {
            String builder =
                    getErrorType().getErrorCodeType() +
                    DOT_SEPARATOR +
                    fieldError.getField() +
                    DOT_SEPARATOR +
                    fieldError.getCode();

            violatedConstraints.add(builder);
        }
        return violatedConstraints;
    }
}
