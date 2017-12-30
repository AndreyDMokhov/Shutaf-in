package com.shutafin.model.exception;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.error.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Slf4j
public class APIExceptionClient {

    public static AbstractAPIException getException(HttpClientErrorException e) {
        String json = e.getResponseBodyAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            JsonNode jsonNode = mapper.readTree(json);
            String errorTypeCode = jsonNode.get("errorTypeCode").asText();
            ErrorType errorType = ErrorType.getById(errorTypeCode);
            return mapper.readValue(json, errorType.getErrorClass());
        } catch (IOException ex) {
            log.error("Unexpected error occurred: ", ex);
            throw new IllegalStateException("Unexpected error occurred");
        }
    }
}
