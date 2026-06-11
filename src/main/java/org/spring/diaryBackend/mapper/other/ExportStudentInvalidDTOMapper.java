package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.other.ExportStudentInvalidDTO;
import org.spring.diaryBackend.model.StudentInvalid;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExportStudentInvalidDTOMapper implements Function<StudentInvalid, ExportStudentInvalidDTO> {
    @Override
    public ExportStudentInvalidDTO apply(StudentInvalid entity) {
        return new ExportStudentInvalidDTO(
                entity.getId(),
                entity.getFio(),
                entity.getSpecialty(),
                entity.getCertificate(),
                entity.getStatus(),
                entity.getLimitationType(),
                entity.getBirthDate(),
                entity.getAddress(),
                entity.getEducationForm(),
                entity.getStudentGroup() != null ? entity.getStudentGroup().getId() : null,
                entity.getStudentGroup() != null ? entity.getStudentGroup().getNumberGroup() : null,
                entity.getStudent() != null ? entity.getStudent().getId() : null,
                entity.getTelephone()
        );
    }
}