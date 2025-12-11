package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.GroupAttendanceDTO;
import org.spring.diaryBackend.dto.other.SingleSubjectAttendanceDTO;
import org.spring.diaryBackend.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findById_IdStudentAndId_IdLesson(Long idStudent, Long idLesson);

    @Query("""
        SELECT NEW org.spring.diaryBackend.dto.other.GroupAttendanceDTO(
            s.id, s.lastName, s.name, s.patronymic, null
        )
        FROM Student s
        WHERE s.idGroup.id = :idGroup
        ORDER BY s.lastName, s.name, s.patronymic
    """)
    List<GroupAttendanceDTO> findStudentsFIOByGroup(@Param("idGroup") Long idGroup);

    @Query("""
        SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.SingleSubjectAttendanceDTO(
            l.id, l.date, a.status, a.comment
        )
        FROM Schedule s
        JOIN Lesson l ON s.id = l.idSchedule.id
        JOIN Attendance a ON l.id = a.idLesson.id
        WHERE a.idStudent.id = :idStudent AND s.idSt.id = :idSt
    """)
    List<SingleSubjectAttendanceDTO> findAttendanceForStudent(@Param("idStudent") Long idStudent,
                                                              @Param("idSt") Long idSt);

    @Modifying
    @Query(value = """
        UPDATE attendance
        SET comment = :comment,
            status = CAST(:status AS attendance_status)
        WHERE id_lesson = :idLesson
          AND id_student = :idStudent
    """, nativeQuery = true)
    void updateAttendanceStatus(@Param("idLesson") Long idLesson,
                                @Param("idStudent") Long idStudent,
                                @Param("comment") String comment,
                                @Param("status") String status);
}