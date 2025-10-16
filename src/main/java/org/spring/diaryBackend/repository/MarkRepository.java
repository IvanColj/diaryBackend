package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.RegularMark;
import org.spring.diaryBackend.model.SemesterMark;
import org.spring.diaryBackend.model.SemesterMarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface MarkRepository extends JpaRepository<SemesterMark, SemesterMarkId> {

    @Query("SELECT m FROM SemesterMark m JOIN m.regularMarks mm WHERE m.id.idStudent = :idStudent and m.id.idSt = :idSt")
    SemesterMark findByStudentAndSubject(@Param("idStudent") Long idStudent, @Param("idSt") Long idSt);

    @Query("SELECT m.regularMarks FROM SemesterMark m JOIN m.regularMarks mm WHERE m.id.idStudent = :idStudent and m.id.idSt = :idSt")
    List<RegularMark> findByStudentSubject(@Param("idStudent") Long idStudent, @Param("idSt") Long idSt);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT number
                    FROM regular_mark
                    WHERE semester_mark_id_st = :semester_mark_id_st
                      AND semester_mark_id_student =
                          (SELECT id FROM student WHERE id_group = :id_group LIMIT 1)
                    ORDER BY number DESC
                    LIMIT 1""")
    Long findLargestNumberRegularMarks(@Param("id_group") Long idGroup, @Param("semester_mark_id_st") Long id_st);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM regular_marks WHERE semester_mark_id_st = :id_st AND semester_mark_id_student = :id_student AND number = :number")
    void deleteMarksNumber(@Param("id_student") Long idStudent,@Param("id_st") Long idSt,@Param("number") Long number);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL delete_mark_number(:id_st, :id_group, :number)"
    )
    void deleteMarksNumberGroupST(@Param("id_st") Long idSt, @Param("id_group") Long idGroup, @Param("number") Long number);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL insert_marks_for_group(:id_group, :id_st, :number_marks, :id_lesson, :new_date_time)"
    )
    void addRegularMarksForGroup(@Param("id_group") Long idGroup, @Param("id_st") Long idSt, @Param("number_marks") Long numberMurks, @Param("id_lesson") Long idLesson, @Param("new_date_time") LocalDateTime new_date_time);
}
