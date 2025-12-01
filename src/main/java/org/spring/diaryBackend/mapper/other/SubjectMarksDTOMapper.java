package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.other.SubjectMarksInfoDTO;
import org.spring.diaryBackend.model.RegularMark;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubjectMarksDTOMapper implements Function<RegularMark, SubjectMarksInfoDTO> {
    @Override
    public SubjectMarksInfoDTO apply(RegularMark regularMark) {
        return new SubjectMarksInfoDTO(
                regularMark.getId().getNumber(),
                regularMark.getValue(),
                regularMark.getIdLesson() != null ?
                regularMark.getIdLesson().getIdSupplement().getComment() : null,
                regularMark.getIdTypeMark() != null ?
                regularMark.getIdTypeMark().getName() : null,
                regularMark.getIdLesson() != null ?
                regularMark.getIdLesson().getDate() : null
        );
    }
}
