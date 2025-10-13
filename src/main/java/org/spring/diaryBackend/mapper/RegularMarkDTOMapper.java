package org.spring.diaryBackend.mapper;

import org.spring.diaryBackend.dto.entity.RegularMarkDTO;
import org.spring.diaryBackend.model.RegularMark;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RegularMarkDTOMapper implements Function<RegularMark, RegularMarkDTO> {
    @Override
    public RegularMarkDTO apply(RegularMark regularMark) {
        if (regularMark == null) {
            return new RegularMarkDTO();
        }
        return new RegularMarkDTO(
                regularMark.getId(),
                regularMark.getValue(),
                regularMark.getIdChange() != null ? regularMark.getIdChange().getId() : null,
                regularMark.getIdLesson() != null ? regularMark.getIdLesson().getId() : null,
                regularMark.getIdTypeMark() != null ? regularMark.getIdTypeMark().getId() : null
        );
    }
}
