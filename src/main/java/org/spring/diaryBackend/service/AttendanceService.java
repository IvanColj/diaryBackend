package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.AttendanceDTO;
import org.spring.diaryBackend.dto.other.AllSubjectsAttendanceDTO;
import org.spring.diaryBackend.dto.other.GroupAttendanceDTO;

import java.util.List;

public interface AttendanceService {
    AttendanceDTO findByStAndStudent(Long idLesson, Long idStudent);
    List<GroupAttendanceDTO> findByGroupAttendance(Long idGroup, Long idSt, Long idTeacher);
    List<AllSubjectsAttendanceDTO> studentAttendance(Long idStudent);

    void update(Long idStudent, AttendanceDTO attendanceDTO);
}
