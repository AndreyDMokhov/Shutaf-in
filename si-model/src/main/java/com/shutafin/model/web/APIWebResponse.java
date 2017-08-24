package com.shutafin.model.web;


import com.shutafin.model.web.error.ErrorResponse;

public class APIWebResponse {

    private ErrorResponse error;
    private Object data;

    public APIWebResponse() {
    }

    public APIWebResponse(ErrorResponse error, Object data) {
        this.error = error;
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
