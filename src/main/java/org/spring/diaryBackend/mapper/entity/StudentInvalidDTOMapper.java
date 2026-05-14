package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.StudentInvalidDTO;
import org.spring.diaryBackend.model.StudentInvalid;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StudentInvalidDTOMapper implements Function<StudentInvalid, StudentInvalidDTO> {
    @Override
    public StudentInvalidDTO apply(StudentInvalid entity) {
        return new StudentInvalidDTO(
                entity.getId(),
                entity.getFio(),
                entity.getSpecialty(),
                entity.getCertificate(),
                entity.getStatus(),
                entity.getLimitationType(),
                entity.getBirthDate(),
                entity.getAddress(),
                entity.getEducationForm()
        );
    }
}
