package org.spring.diaryBackend.mapper;

import org.spring.diaryBackend.dto.entity.SubjectDTO;
import org.spring.diaryBackend.model.Subject;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubjectDTOMapper implements Function<Subject, SubjectDTO> {
    @Override
    public SubjectDTO apply(Subject subject) {
        if (subject == null) {
            return new SubjectDTO();
        }
        return new SubjectDTO(
                subject.getId(),
                subject.getSubjectName()
        );
    }
}
