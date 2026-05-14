package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> findAllRooms();

    RoomDTO createRoom(RoomDTO dto);

    void assignStaff(Long roomId, Long staffId);

    List<RoomDTO> findFreeRooms();
}