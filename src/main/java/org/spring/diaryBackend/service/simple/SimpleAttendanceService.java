package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dbEnum.AttendanceStatus;
import org.spring.diaryBackend.dto.entity.AttendanceDTO;
import org.spring.diaryBackend.dto.other.GroupAttendanceDTO;
import org.spring.diaryBackend.mapper.entity.AttendanceDTOMapper;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.repository.AttendanceRepository;
import org.spring.diaryBackend.repository.SubgroupRepository;
import org.spring.diaryBackend.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleAttendanceService implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    private final SubgroupRepository subgroupRepository;

    private final AttendanceDTOMapper attendanceDTOMapper;

    @Override
    public AttendanceDTO findByStAndStudent(Long idLesson, Long idStudent) {
        return attendanceDTOMapper.apply(attendanceRepository.findById_IdStudentAndId_IdLesson(idStudent, idLesson));
    }

    @Override
    public List<GroupAttendanceDTO> findByGroupAttendance(Long idGroup, Long idSt, Long idTeacher) {
        List<GroupAttendanceDTO> attendances = attendanceRepository.findBaseInfo(idGroup);
        if (subgroupRepository.findByIdStAndIdTeacher(idSt, idTeacher) != null) {
            List<Long> studentsId = subgroupRepository.findByIdStAndIdTeacher(idSt, idTeacher).getStudents().stream().map(Student::getId).toList();
            attendances = attendances.stream()
                    .filter(attendance -> studentsId.contains(attendance.getIdStudent()))
                    .toList();
        }
        for (GroupAttendanceDTO attendance : attendances) {
            attendance.setAttendances(attendanceRepository.findAttendanceStudent(attendance.getIdStudent(), idSt));
        }

        return attendances;
    }

    @Override
    @Transactional
    public void update(Long idStudent, AttendanceDTO attendanceDTO) {
        if (attendanceDTO.getStatus() != null || attendanceDTO.getComment() != null) {
            String statusCode = AttendanceStatus.toCode(attendanceDTO.getStatus());

            if (attendanceDTO.getStatus() != null && statusCode == null) {
                throw new IllegalArgumentException("Неизвестный статус: " + attendanceDTO.getStatus());
            }

            attendanceRepository.updateAttendanceNative(
                    attendanceDTO.getIdLesson(),
                    idStudent,
                    attendanceDTO.getComment(),
                    statusCode
            );
        }
    }

    private String convertStatusToCode(String status) {
        if (status == null) {
            return null;
        }

        try {
            AttendanceStatus enumStatus = AttendanceStatus.valueOf(status);
            return enumStatus.getCode();
        } catch (IllegalArgumentException e) {
            return status;
        }
    }
}
