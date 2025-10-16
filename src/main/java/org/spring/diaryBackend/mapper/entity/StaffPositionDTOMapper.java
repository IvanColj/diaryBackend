package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.StaffPositionDTO;
import org.spring.diaryBackend.model.StaffPosition;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StaffPositionDTOMapper implements Function<StaffPosition, StaffPositionDTO> {
    @Override
    public StaffPositionDTO apply(StaffPosition staffPosition) {
        return new StaffPositionDTO(
                staffPosition.getId(),
                staffPosition.getName().getLabel()
        );
    }
}
