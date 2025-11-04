package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.LessonInfoDTO;
import org.spring.diaryBackend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(
            value = """
                    SELECT new org.spring.diaryBackend.dto.other.LessonInfoDTO(
                        l.id, l.numberWeek, s.dayWeek, s.typeWeek, s.numPair, s.replacement
                    )
                    FROM Schedule s JOIN s.lessons l
                    WHERE s.idSt.id = :idSt AND s.idGroup.id = :idGroup
                    
                    """)
    List<LessonInfoDTO> findByLessonInfo(@Param("idSt") Long idSt, @Param("idGroup") Long idGroup);
}
