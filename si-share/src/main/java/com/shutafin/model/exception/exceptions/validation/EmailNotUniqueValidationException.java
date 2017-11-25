package com.shutafin.model.exception.exceptions.validation;


import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class EmailNotUniqueValidationException extends ValidationException {

    private static final String FIELD_NAME = "Email";
    private static final String CODE = "NotUnique";

    public EmailNotUniqueValidationException(String systemMessage) {
        super(systemMessage);
    }

    public EmailNotUniqueValidationException() {
    }

    @Override
    protected List<String> getFieldErrors() {
        return new ArrayList<String>() {{
            add(FIELD_NAME + "." + CODE);
        }};
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.EMAIL_DUPLICATION_EXCEPTION;
    }
}
