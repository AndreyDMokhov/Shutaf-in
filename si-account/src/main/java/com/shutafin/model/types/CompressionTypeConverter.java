package com.shutafin.model.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CompressionTypeConverter implements AttributeConverter<CompressionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CompressionType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public CompressionType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : CompressionType.getById(dbData);
    }
}
