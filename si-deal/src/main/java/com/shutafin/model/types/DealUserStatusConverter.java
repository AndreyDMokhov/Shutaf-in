package com.shutafin.model.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DealUserStatusConverter implements AttributeConverter<DealUserStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DealUserStatus attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public DealUserStatus convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : DealUserStatus.getById(dbData);
    }
}
