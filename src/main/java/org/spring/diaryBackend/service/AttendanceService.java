package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.AttendanceDTO;
import org.spring.diaryBackend.dto.other.GroupAttendanceDTO;
import org.spring.diaryBackend.dto.other.StudentAttendanceDTO;

import java.util.List;

public interface AttendanceService {
    AttendanceDTO findByStAndStudent(Long idLesson, Long idStudent);

    List<GroupAttendanceDTO> findByGroupAttendance(Long idGroup, Long idSt, Long idTeacher);

    void update(Long idStudent, AttendanceDTO attendanceDTO);

    List<StudentAttendanceDTO> studentAttendance(Long idStudent);
}
