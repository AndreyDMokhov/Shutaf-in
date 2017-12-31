package com.shutafin.handlers;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity getClientAbortResponse(ClientAbortException exception) {

        log.debug("ClientAbortException: ", exception);
        log.warn("ClientAbortException detected " + exception.getMessage());

        return new ResponseEntity(HttpStatus.GATEWAY_TIMEOUT);
    }


}
