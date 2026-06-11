package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStaffOwnerIsNull();
}