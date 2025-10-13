package org.spring.diaryBackend.mapper;

import lombok.RequiredArgsConstructor;
import org.spring.diaryBackend.dto.entity.SemesterMarkDTO;
import org.spring.diaryBackend.model.SemesterMark;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SemesterMarkDTOMapper implements Function<SemesterMark, SemesterMarkDTO> {

    private final RegularMarkDTOMapper regularMarkDTOMapper;

    @Override
    public SemesterMarkDTO apply(SemesterMark semesterMark) {
        if (semesterMark == null) {
            return new SemesterMarkDTO();
        }
        return new SemesterMarkDTO(
                semesterMark.getId(),
                semesterMark.getCertification(),
                semesterMark.getRegularMarks() != null ? semesterMark.getRegularMarks().stream().toList().stream().map(regularMarkDTOMapper).toList() : null
        );
    }
}
