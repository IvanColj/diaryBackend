package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Change;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChangeRepository extends JpaRepository<Change, Long> {

    @Query(
            """
            SELECT c FROM Change c
            JOIN c.regularMarks rm
            WHERE rm.id.semesterMarkIdSt = :idSt
                AND rm.id.semesterMarkIdStudent = :idStudent
                AND rm.id.number = :numberMark
            """
    )
    List<Change> findByAllChangeMark(@Param("idSt") Long idSt,@Param("idStudent") Long idStudent,@Param("numberMark") Long numberMark);

    @Modifying
    @Query(
            nativeQuery = true,
            value =
            """
            INSERT INTO regular_mark_change values (:id_st, :id_student, :number_mark, :id_change);
            """
    )
    void insertChange(@Param("id_st") Long idSt, @Param("id_student") Long idStudent, @Param("number_mark") Long numberMark, @Param("id_change") Long idChange);
}
