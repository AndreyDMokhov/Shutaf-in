package com.shutafin.handler;

import com.shutafin.model.error.ErrorResponse;
import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class HttpResponseExceptionHandler {

    @ExceptionHandler(AbstractAPIException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> getAPIExceptionResponse(AbstractAPIException exception) {

        log.error("API exception: ", exception);

        HttpStatus httpStatus = HttpStatus.valueOf(exception.getErrorType().getHttpCode());
        return new ResponseEntity<>(exception.getErrorResponse(), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> getSystemExceptionResponse(Exception exception) {

        log.error("Internal error: ", exception);

        ErrorType errorType = ErrorType.SYSTEM;

        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), errorType);

        HttpStatus httpStatus = HttpStatus.valueOf(errorType.getHttpCode());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
