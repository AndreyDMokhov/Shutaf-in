package com.shutafin.controller;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AbstractAPIException.class)
    @ResponseBody
    public ResponseEntity<APIWebResponse> getAPIExceptionResponse(AbstractAPIException exception) {

        exception.printStackTrace();

        APIWebResponse apiWebResponse = new APIWebResponse();
        apiWebResponse.setData(null);
        apiWebResponse.setError(exception.getErrorResponse());

        HttpStatus httpStatus = HttpStatus.valueOf(exception.getErrorType().getHttpCode());
        return new ResponseEntity<>(apiWebResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIWebResponse> getSystemExceptionResponse(Exception exception) {
        exception.printStackTrace();

        ErrorType errorType = ErrorType.SYSTEM;

        APIWebResponse apiWebResponse = new APIWebResponse();
        apiWebResponse.setData(null);

        ErrorResponse responseError = new ErrorResponse(exception.getMessage(), errorType);

        apiWebResponse.setError(responseError);

        HttpStatus httpStatus = HttpStatus.valueOf(errorType.getHttpCode());

        return new ResponseEntity<APIWebResponse>(apiWebResponse, httpStatus);
    }

}
