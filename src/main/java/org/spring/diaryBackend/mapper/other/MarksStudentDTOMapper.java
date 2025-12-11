package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.other.MarksStudentDTO;
import org.spring.diaryBackend.model.RegularMark;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MarksStudentDTOMapper implements Function<RegularMark, MarksStudentDTO> {
    @Override
    public MarksStudentDTO apply(RegularMark regularMark) {
        if (regularMark != null) {
            return new MarksStudentDTO(
                    regularMark.getId() != null ? regularMark.getId().getNumber() : null,
                    regularMark.getValue()
            );
        } else {
            return null;
        }
    }
}
