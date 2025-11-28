package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.AttendanceDTO;
import org.spring.diaryBackend.model.Attendance;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AttendanceDTOMapper implements Function<Attendance, AttendanceDTO> {
    @Override
    public AttendanceDTO apply(Attendance attendance) {
        return new AttendanceDTO(
                attendance.getIdLesson().getId(),
                attendance.getIdStudent().getId(),
                attendance.getStatus() != null ? attendance.getStatus().toString() : null,
                attendance.getComment()
        );
    }
}
