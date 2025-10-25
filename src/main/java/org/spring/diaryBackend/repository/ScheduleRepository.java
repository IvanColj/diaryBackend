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
                       s.idGroup.id, s.idGroup.numberGroup, s.subgroup.id, s.replacement
                       ) FROM Schedule s WHERE s.idGroup.id = :idGroup
            """)
    List<ScheduleWeekGroupDTO> findScheduleWeekGroup(@Param("idGroup") Long idGroup);
}
