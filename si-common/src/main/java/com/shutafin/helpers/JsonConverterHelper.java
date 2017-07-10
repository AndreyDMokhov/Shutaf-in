package com.shutafin.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class JsonConverterHelper<T> {

    private final ObjectMapper mapper = new ObjectMapper();



    public T getObject(String json, Object objectToConvert) {
        try {

            return mapper.readValue(json, (Class<T>) objectToConvert);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Json parsing exception");
        }
    }

    public String getJson(T objectToConvert) {
        try {
            return mapper.writeValueAsString(objectToConvert);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
            throw new IllegalArgumentException("Json processing exception");
        }
    }
}
