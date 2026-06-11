package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.StudentOrphanDTO;
import org.spring.diaryBackend.model.StudentOrphan;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StudentOrphanDTOMapper implements Function<StudentOrphan, StudentOrphanDTO> {
    @Override
    public StudentOrphanDTO apply(StudentOrphan entity) {
        return new StudentOrphanDTO(
                entity.getId(),
                entity.getFio(),
                entity.getSpecialty(),
                entity.getBirthDate(),
                entity.getParentInfo(),
                entity.getTelephone(),
                entity.getRegistrationAddress(),
                entity.getGuardian(),
                entity.getEducationForm(),
                entity.getStudentGroup() != null ? entity.getStudentGroup().getNumberGroup() : null,
                entity.getStudent() != null ? entity.getStudent().getId() : null
        );
    }
}
