package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StaffPositionDTO;
import org.spring.diaryBackend.mapper.StaffPositionDTOMapper;
import org.spring.diaryBackend.repository.StaffPositionRepository;
import org.spring.diaryBackend.service.StaffPositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleStaffPositionService implements StaffPositionService {
    private final StaffPositionRepository staffPositionRepository;

    private final StaffPositionDTOMapper staffPositionDTOMapper;

    @Override
    public List<StaffPositionDTO> findAllStaffPosition() {
        return staffPositionRepository.findAll().stream().map(staffPositionDTOMapper).toList();
    }
}
