package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.other.ExportStudentOrphanDTO;
import org.spring.diaryBackend.model.StudentOrphan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExportStudentOrphanDTOMapper implements Function<StudentOrphan, ExportStudentOrphanDTO> {
    @Override
    public ExportStudentOrphanDTO apply(StudentOrphan entity) {
        return new ExportStudentOrphanDTO(
                entity.getId(),
                entity.getFio(),
                entity.getSpecialty(),
                entity.getBirthDate(),
                entity.getParentInfo(),
                entity.getTelephone(),
                entity.getRegistrationAddress(),
                entity.getGuardian(),
                entity.getEducationForm(),
                entity.getStudentGroup() != null ? entity.getStudentGroup().getId() : null,
                entity.getStudentGroup() != null ? entity.getStudentGroup().getNumberGroup() : null,
                entity.getStudent() != null ? entity.getStudent().getId() : null
        );
    }
}