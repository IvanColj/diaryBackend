package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StaffPositionDTO;
import org.spring.diaryBackend.service.StaffPositionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/staffPositions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StaffPositionController {
    private final StaffPositionService staffPositionService;

    @GetMapping()
    public List<StaffPositionDTO> findByAllStaffPositions() {
        return staffPositionService.findAllStaffPosition();
    }
}