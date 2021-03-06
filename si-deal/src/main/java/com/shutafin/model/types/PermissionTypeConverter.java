package com.shutafin.model.types;

import com.shutafin.model.web.deal.PermissionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PermissionTypeConverter implements AttributeConverter<PermissionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PermissionType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public PermissionType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : PermissionType.getById(dbData);
    }
}
