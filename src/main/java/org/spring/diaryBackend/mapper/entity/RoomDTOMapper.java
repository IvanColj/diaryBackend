package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.RoomDTO;
import org.spring.diaryBackend.model.Room;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoomDTOMapper implements Function<Room, RoomDTO> {
    @Override
    public RoomDTO apply(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getStaffOwner() != null ? room.getStaffOwner().getId() : null
        );
    }
}