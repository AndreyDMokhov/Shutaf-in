package com.shutafin.exception.exceptions.validation;

import com.shutafin.exception.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class EmailNotUniqueValidationException extends ValidationException {

    private static final String FIELD_NAME = "Email";
    private static final String CODE = "NotUnique";

    public EmailNotUniqueValidationException(String systemMessage) {
        super(systemMessage);
    }

    public EmailNotUniqueValidationException() {
    }

    @Override
    protected Map<String, String> getFieldErrors() {
        return new HashMap<String, String>() {{
            put(FIELD_NAME, CODE);
        }};
    }
}
