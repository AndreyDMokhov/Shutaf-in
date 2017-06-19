package com.shutafin.model.web.error.errors;

import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;

import java.util.List;


public class InputValidationError extends ErrorResponse {

    private List<String> errors;



    public InputValidationError(String errorMessage, ErrorType errorType, List<String> errors) {
        super(errorMessage, errorType);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
