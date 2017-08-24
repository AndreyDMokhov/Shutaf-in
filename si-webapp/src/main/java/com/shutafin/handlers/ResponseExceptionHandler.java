package com.shutafin.handlers;

import com.shutafin.exception.AbstractAPIException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(AbstractAPIException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> getAPIExceptionResponse(AbstractAPIException exception) {

        exception.printStackTrace();

        HttpStatus httpStatus = HttpStatus.valueOf(exception.getErrorType().getHttpCode());
        return new ResponseEntity<>(exception.getErrorResponse(), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> getSystemExceptionResponse(Exception exception) {
        exception.printStackTrace();

        ErrorType errorType = ErrorType.SYSTEM;


        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), errorType);


        HttpStatus httpStatus = HttpStatus.valueOf(errorType.getHttpCode());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}
