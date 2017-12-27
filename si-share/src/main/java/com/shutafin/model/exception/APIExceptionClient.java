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

    public static void getException(HttpClientErrorException e) {
        String json = e.getResponseBodyAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            JsonNode jsonNode = mapper.readTree(json);
            String errorTypeCode = jsonNode.get("errorTypeCode").asText();
            String errorMessage = jsonNode.get("errorMessage").asText();

            if (errorTypeCode != "INP") {
                ErrorType errorType = ErrorType.getById(errorTypeCode);
                String systemMessage = "{\"systemMessage\":" + "\"" + errorMessage + "\"}";
                AbstractAPIException exception = mapper.readValue(systemMessage, errorType.getErrorClass());
                throw exception;
            }

        } catch (IOException ex) {
            log.error("Unexpected error occurred: ", ex);
            throw new IllegalStateException("Unexpected error occurred");
        }
    }
}
