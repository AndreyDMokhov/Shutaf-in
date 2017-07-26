package com.shutafin.controller;

import java.util.ArrayList;
import java.util.List;

public class Error {

    private String errorMessage;
    private String errorTypeCode;
    private List<String> errors = new ArrayList<String>();

    public Error() {
    }

    public Error(String errorMessage, String errorTypeCode, List<String> errors) {
        this.errorMessage = errorMessage;
        this.errorTypeCode = errorTypeCode;
        this.errors = errors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorTypeCode() {
        return errorTypeCode;
    }

    public void setErrorTypeCode(String errorTypeCode) {
        this.errorTypeCode = errorTypeCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
