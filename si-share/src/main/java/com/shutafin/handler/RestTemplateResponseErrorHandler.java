package com.shutafin.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.error.ErrorType;
import com.shutafin.model.exception.AbstractAPIException;
import com.shutafin.model.exception.exceptions.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        AbstractAPIException apiException;

        String json = IOUtils.toString(response.getBody());
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            JsonNode jsonNode = mapper.readTree(json);
            String errorTypeCode = jsonNode.get("errorTypeCode").asText();
            ErrorType errorType = ErrorType.getById(errorTypeCode);

            apiException = errorType.getErrorClass().newInstance();
        } catch (IllegalAccessException e) {
            log.error("REST deserialization error during error parsing: {} ", e);
            throw new SystemException();
        } catch (InstantiationException e) {
            log.error("Error occurred during exception new instance creation: {} ", e);
            throw new SystemException();
        }

        throw apiException;
    }
}
