package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.RoomDTO;
import org.spring.diaryBackend.mapper.entity.RoomDTOMapper;
import org.spring.diaryBackend.repository.RoomRepository;
import org.spring.diaryBackend.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleRoomService implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomDTOMapper roomDTOMapper;

    @Override
    public List<RoomDTO> findAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomDTOMapper)
                .toList();
    }
}