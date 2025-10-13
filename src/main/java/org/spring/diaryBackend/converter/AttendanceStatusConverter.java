package org.spring.diaryBackend.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.spring.diaryBackend.dbEnum.AttendanceStatus;

@Converter(autoApply = true)
public class AttendanceStatusConverter implements AttributeConverter<AttendanceStatus, String> {

    @Override
    public String convertToDatabaseColumn(AttendanceStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public AttendanceStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (AttendanceStatus status : AttendanceStatus.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}
