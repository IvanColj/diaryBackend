package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.RoomDTO;
import org.spring.diaryBackend.service.RoomService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.findAllRooms();
    }
}