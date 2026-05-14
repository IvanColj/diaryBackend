package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.RoomDTO;
import org.spring.diaryBackend.mapper.entity.RoomDTOMapper;
import org.spring.diaryBackend.model.Room;
import org.spring.diaryBackend.model.Staff;
import org.spring.diaryBackend.repository.RoomRepository;
import org.spring.diaryBackend.repository.StaffRepository;
import org.spring.diaryBackend.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleRoomService implements RoomService {
    private final RoomRepository roomRepository;
    private final StaffRepository staffRepository;
    private final RoomDTOMapper roomDTOMapper;

    @Override
    public List<RoomDTO> findAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomDTOMapper)
                .toList();
    }

    @Override
    @Transactional
    public void assignStaff(Long roomId, Long staffId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        Staff staff = staffRepository.findById(staffId).orElseThrow();
        room.setStaffOwner(staff);
        roomRepository.save(room);
    }

    @Override
    public List<RoomDTO> findFreeRooms() {
        return roomRepository.findByStaffOwnerIsNull()
                .stream()
                .map(roomDTOMapper)
                .toList();
    }

    @Override
    @Transactional
    public RoomDTO createRoom(RoomDTO dto) {
        Room room = new Room();
        room.setName(dto.getName());
        if (dto.getIdStaffOwner() != null) {
            Staff staff = staffRepository.findById(dto.getIdStaffOwner())
                    .orElseThrow(() -> new RuntimeException("Сотрудник с id " + dto.getIdStaffOwner() + " не найден"));
            room.setStaffOwner(staff);
        }
        else {
            room.setStaffOwner(null);
        }
        return roomDTOMapper.apply(roomRepository.save(room));
    }

}