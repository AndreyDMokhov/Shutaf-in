package com.shutafin.model.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DealStatusConverter implements AttributeConverter<DealStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DealStatus attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public DealStatus convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : DealStatus.getById(dbData);
    }
}
