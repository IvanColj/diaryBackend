package org.spring.diaryBackend.mapper.other;

import org.spring.diaryBackend.dto.entity.RegularMarkDTO;
import org.spring.diaryBackend.dto.other.MarksStudentDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MarksStudentDTOMapper implements Function<RegularMarkDTO, MarksStudentDTO> {
    @Override
    public MarksStudentDTO apply(RegularMarkDTO regularMarkDTO) {
        if (regularMarkDTO.getId() != null) {
            return new MarksStudentDTO(
                    regularMarkDTO.getId().getNumber(),
                    regularMarkDTO.getValue()
            );
        }
        else {
            return null;
        }
    }
}
