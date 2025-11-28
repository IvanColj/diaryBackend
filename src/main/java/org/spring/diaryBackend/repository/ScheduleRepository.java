package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.ScheduleWeekGroupDTO;
import org.spring.diaryBackend.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
            SELECT NEW org.spring.diaryBackend.dto.other.ScheduleWeekGroupDTO(
                       s.id, s.dayWeek, s.typeWeek,
                       s.numPair, s.room, s.idSt.id,
                       s.idSt.idSubject.id, s.idSt.idSubject.subjectName,
                       null, null, null, null,
                       s.idGroup.id, s.idGroup.numberGroup, s.subgroup.id, s.replacement, s.dateReplacement
                       ) FROM Schedule s WHERE s.idGroup.id = :idGroup
            """)
    List<ScheduleWeekGroupDTO> findScheduleWeekGroup(@Param("idGroup") Long idGroup);

    @Query(
            nativeQuery = true,
            value = "SELECT s.id, s.day_week, s.type_week, s.num_pair, " +
                    "s.room, s.id_st, st.id_subject, s3.subject_name, " +
                    "ts.id_teacher, s2.last_name, s2.name, s2.patronymic, " +
                    "s.id_group, sg.number_group, s.subgroup, s.replacement, s.date_replacement " +
                    "FROM schedule s JOIN subject_teacher st ON st.id = s.id_st " +
                    "JOIN teachers_st ts ON st.id = ts.id_st " +
                    "JOIN staff s2 ON s2.id = ts.id_teacher " +
                    "JOIN subject s3 ON st.id_subject = s3.id " +
                    "JOIN student_group sg ON s.id_group = sg.id " +
                    "WHERE s.subgroup = :id_teacher AND ts.id_teacher = :id_teacher " +
                    "UNION\n" +
                    "SELECT s.id, s.day_week, s.type_week, s.num_pair, " +
                    "s.room, s.id_st, st.id_subject, s3.subject_name, " +
                    "ts.id_teacher, s2.last_name, s2.name, s2.patronymic, " +
                    "s.id_group, sg.number_group, s.subgroup, s.replacement, s.date_replacement " +
                    "FROM schedule s JOIN subject_teacher st ON st.id = s.id_st " +
                    "JOIN teachers_st ts ON st.id = ts.id_st " +
                    "JOIN staff s2 ON s2.id = ts.id_teacher " +
                    "JOIN subject s3 ON st.id_subject = s3.id " +
                    "JOIN student_group sg ON s.id_group = sg.id " +
                    "WHERE st.id NOT IN " +
                    "(SELECT sub.id_st from subgroup sub where sub.id_st = st.id) " +
                    "and ts.id_teacher = :id_teacher")
    List<Object[]> findBySchedule(@Param("id_teacher") Long idTeacher);
}
