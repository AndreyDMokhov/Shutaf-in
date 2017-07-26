package com.shutafin.controller;

public class ApiResponse {

    private int status;
    private Error error;
    private  String data;

    public ApiResponse() {
    }

    public ApiResponse(int status, Error error, String data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
