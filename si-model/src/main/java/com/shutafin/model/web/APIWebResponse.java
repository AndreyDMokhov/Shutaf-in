package com.shutafin.model.web;


import com.shutafin.model.web.error.ErrorResponse;

public class APIWebResponse {

    private ErrorResponse error;
    private DataResponse data;

    public APIWebResponse() {
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }
}
