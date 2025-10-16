package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.other.SubjectMarksDTO;
import org.spring.diaryBackend.model.Change;
import org.spring.diaryBackend.model.RegularMark;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubjectMarksDTOMapper implements Function<RegularMark, SubjectMarksDTO> {
    @Override
    public SubjectMarksDTO apply(RegularMark regularMark) {
        return new SubjectMarksDTO(
                regularMark.getId().getNumber(),
                regularMark.getValue(),
                regularMark.getIdLesson() != null ?
                regularMark.getIdLesson().getIdSupplement().getComment() : null,
                regularMark.getIdTypeMark().getName(),
                regularMark.getIdLesson() != null ?
                regularMark.getIdLesson().getDate() : null,
                regularMark.getChanges() != null ?
                regularMark.getChanges().stream().map(Change::getId).toList() : null
        );
    }
}
