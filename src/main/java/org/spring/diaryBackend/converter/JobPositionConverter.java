package org.spring.diaryBackend.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.spring.diaryBackend.dbEnum.JobPosition;

@Converter(autoApply = true)
public class JobPositionConverter implements AttributeConverter<JobPosition, String> {

    @Override
    public String convertToDatabaseColumn(JobPosition attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getLabel();
    }

    @Override
    public JobPosition convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (JobPosition position : JobPosition.values()) {
            if (position.getLabel().equals(dbData)) {
                return position;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}