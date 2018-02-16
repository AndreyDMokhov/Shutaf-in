package com.shutafin.model.types;

import com.shutafin.model.web.deal.DealUserPermissionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DealUserPermissionTypeConverter implements AttributeConverter<DealUserPermissionType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DealUserPermissionType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public DealUserPermissionType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : DealUserPermissionType.getById(dbData);
    }
}
