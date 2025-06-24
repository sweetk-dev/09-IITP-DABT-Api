package com.sweetk.iitp.api.constant;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class DataStatusTypeConverter implements AttributeConverter<DataStatusType, String> {
    @Override
    public String convertToDatabaseColumn(DataStatusType attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public DataStatusType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (DataStatusType type : DataStatusType.values()) {
            if (type.getCode().equals(dbData)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DataStatusType code: " + dbData);
    }
} 