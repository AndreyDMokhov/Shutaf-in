package com.shutafin.exception.exceptions.validation;


import com.shutafin.exception.exceptions.ValidationException;
import com.shutafin.model.web.error.ErrorType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class InputValidationException extends ValidationException {

    private static final String DOT_SEPARATOR = ".";

    private BindingResult result;

    public InputValidationException(BindingResult result) {
        this.result = result;
    }

    @Override
    protected List<String> getFieldErrors() {
        List<String> list = new ArrayList<>();

        for (FieldError fieldError : this.result.getFieldErrors()) {
            String builder =
                    getErrorType().getErrorCodeType() +
                            DOT_SEPARATOR +
                            fieldError.getField() +
                            DOT_SEPARATOR +
                            fieldError.getCode();
            list.add(builder);
        }
        return list;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.INPUT;
    }
}
