package org.spring.diaryBackend.mapper.entity;

import lombok.RequiredArgsConstructor;
import org.spring.diaryBackend.dto.entity.RegularMarkDTO;
import org.spring.diaryBackend.model.RegularMark;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RegularMarkDTOMapper implements Function<RegularMark, RegularMarkDTO> {

    private final TypeMarkDTOMapper typeMarkDTOMapper;

    private final ChangeDTOMapper changeDTOMapper;

    @Override
    public RegularMarkDTO apply(RegularMark regularMark) {
        if (regularMark == null) {
            return new RegularMarkDTO();
        }
        return new RegularMarkDTO(
                regularMark.getId(),
                regularMark.getValue(),
                regularMark.getIdLesson() != null ? regularMark.getIdLesson().getId() : null,
                regularMark.getIdTypeMark() != null ? typeMarkDTOMapper.apply(regularMark.getIdTypeMark()) : null,
                regularMark.getChanges().stream().map(changeDTOMapper).toList()
        );
    }
}
