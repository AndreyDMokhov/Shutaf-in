package com.shutafin.model.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DealSnapshotInfoConverter implements AttributeConverter<DealSnapshotInfo, String> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DealSnapshotInfo data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert to Json", e);
        }
    }

    @Override
    public DealSnapshotInfo convertToEntityAttribute(String json) {
        try {
            return mapper.readValue(json, DealSnapshotInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert from Json", e);
        }
    }
}