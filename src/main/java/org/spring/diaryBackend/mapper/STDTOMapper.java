package org.spring.diaryBackend.mapper;

import org.spring.diaryBackend.dto.entity.SubjectTeacherDTO;
import org.spring.diaryBackend.model.StudentGroup;
import org.spring.diaryBackend.model.SubjectTeacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class STDTOMapper implements Function<SubjectTeacher, SubjectTeacherDTO> {
    @Override
    public SubjectTeacherDTO apply(SubjectTeacher subjectTeacher) {
        if (subjectTeacher == null) {
            return new SubjectTeacherDTO();
        }
        return new SubjectTeacherDTO(
                subjectTeacher.getId(),
                subjectTeacher.getIdTeacher() != null ? subjectTeacher.getIdTeacher().getId() : null,
                subjectTeacher.getIdSubject() != null ? subjectTeacher.getIdSubject().getId() : null,
                subjectTeacher.getGroups() != null ? subjectTeacher.getGroups().stream().map(StudentGroup::getId).toList() : null
        );
    }
}
