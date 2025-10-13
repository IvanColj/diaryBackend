package org.spring.diaryBackend.service;


import org.spring.diaryBackend.dto.entity.StaffPositionDTO;

import java.util.List;

public interface StaffPositionService {
    List<StaffPositionDTO> findAllStaffPosition();
}
