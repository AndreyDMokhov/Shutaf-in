package com.shutafin.exception.exceptions.validation;


import com.shutafin.exception.exceptions.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class InputValidationException extends ValidationException {
    private BindingResult result;

    public InputValidationException(BindingResult result) {
        this.result = result;
    }

    @Override
    protected Map<String, String> getFieldErrors() {
        Map<String, String> map = new HashMap<>();

        for (FieldError fieldError : this.result.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getCode());
        }
        return map;
    }
}
