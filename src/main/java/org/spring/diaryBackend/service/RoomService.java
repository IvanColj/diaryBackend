package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> findAllRooms();
}