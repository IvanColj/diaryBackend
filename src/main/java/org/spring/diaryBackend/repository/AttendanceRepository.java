package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.AttendanceStudentDTO;
import org.spring.diaryBackend.dto.other.GroupAttendanceDTO;
import org.spring.diaryBackend.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findById_IdStudentAndId_IdLesson(Long idStudent, Long idLesson);

    @Query("SELECT NEW org.spring.diaryBackend.dto.other.GroupAttendanceDTO(s.id, s.lastName, s.name, s.patronymic, null) FROM Student s WHERE s.idGroup.id = :idGroup ORDER BY s.lastName, s.name, s.patronymic")
    List<GroupAttendanceDTO> findBaseInfo(@Param("idGroup") Long idGroup);

    @Query("SELECT NEW org.spring.diaryBackend.dto.other.AttendanceStudentDTO(" +
            "l.id, l.date, CAST(a.status AS string), a.comment" +
            ") " +
            "FROM Schedule s " +
            "JOIN Lesson l ON s.id = l.idSchedule.id " +
            "JOIN Attendance a ON l.id = a.idLesson.id " +
            "WHERE a.idStudent.id = :idStudent AND s.idSt.id = :idSt ")
    List<AttendanceStudentDTO> findAttendanceStudent(@Param("idStudent") Long idStudent, @Param("idSt") Long idSt);
}
