package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.LessonInfoDTO;
import org.spring.diaryBackend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(
            value = """
                    SELECT new org.spring.diaryBackend.dto.other.LessonInfoDTO(
                        l.id, l.numberWeek, l.date, s.dayWeek, s.typeWeek, s.numPair, s.replacement
                    )
                    FROM Schedule s JOIN s.lessons l
                    WHERE s.idSt.id = :idSt AND s.idGroup.id = :idGroup
                    
                    """)
    List<LessonInfoDTO> findByLessonInfo(@Param("idSt") Long idSt, @Param("idGroup") Long idGroup);

    @Query(
            value = """
                    SELECT DISTINCT l.date
                    FROM Schedule s
                    JOIN s.lessons l
                    JOIN l.regularMarks rm
                    WHERE s.idSt.id = :idSt AND s.idGroup.id = :idGroup
                    ORDER BY l.date
                    
                    
                    """)
    List<LocalDate> findByLessonSubject(@Param("idSt") Long idSt, @Param("idGroup") Long idGroup);

    @Modifying
    @Query(
            nativeQuery = true,
            value =
                    """
                    UPDATE lesson SET id_supplement = NULL WHERE id_supplement = :id_supplement;
                    """
    )
    void updateLesson(@Param("id_supplement") Long idSupplement);
}
