package org.spring.diaryBackend.mapper.entity;

import lombok.RequiredArgsConstructor;
import org.spring.diaryBackend.dto.entity.StaffDTO;
import org.spring.diaryBackend.model.Staff;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class StaffDTOMapper implements Function<Staff, StaffDTO> {

    private final StaffPositionDTOMapper staffPositionDTOMapper;

    @Override
    public StaffDTO apply(Staff staff) {
        if (staff == null) {
            return new StaffDTO();
        }
        return new StaffDTO(
                staff.getId(),
                staff.getPatronymic(),
                staff.getName(),
                staff.getLastName(),
                staff.getLogin(),
                staff.getPassword(),
                staff.getEmail(),
                staff.getStaffPositions() != null ?
                staff.getStaffPositions().stream().toList().stream().map(staffPositionDTOMapper).toList() : null
        );
    }
}
