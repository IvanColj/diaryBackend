package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.RoomDTO;
import org.spring.diaryBackend.service.RoomService;
import org.springframework.web.bind.annotation.*;

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

    // Назначить кабинет сотруднику
    @PatchMapping("assign/{roomId}/{staffId}")
    public void assignStaff(@PathVariable Long roomId, @PathVariable Long staffId) {
        roomService.assignStaff(roomId, staffId);
    }

    @PostMapping("save")
    public RoomDTO createRoom(@RequestBody RoomDTO dto) {
        return roomService.createRoom(dto);
    }

    // Получить все свободные кабинеты
    @GetMapping("free")
    public List<RoomDTO> getFreeRooms() {
        return roomService.findFreeRooms();
    }
}