package com.gestionnaire_de_stage.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Short> {

    @Override
    public Short convertToDatabaseColumn(Year attribute) {
        return (short) attribute.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Short dbValue) {
        return Year.of(dbValue);
    }
}
