package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.SemesterMarks;
import org.spring.diaryBackend.model.SemesterMarksId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MarksRepository extends JpaRepository<SemesterMarks, SemesterMarksId> {
    @Query(
            nativeQuery = true,
            value = "SELECT id_st, id_student, certification FROM marks OFFSET :offset LIMIT :limit")
    List<SemesterMarks> findByAllMarks(@Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT m FROM SemesterMarks m JOIN FETCH m.regularMarks mm WHERE m.id.idStudent = :marks_id_student")
    List<SemesterMarks> findByStudentMarks(@Param("marks_id_student") Long marks_id_student);

    @Query("SELECT m FROM SemesterMarks m JOIN FETCH m.regularMarks mm WHERE m.id.idSt = :marks_id_st")
    List<SemesterMarks> findByObjectMarks(@Param("marks_id_st") Long marks_id_st);

    @Query("SELECT m FROM SemesterMarks m JOIN FETCH m.regularMarks mm WHERE m.id.idStudent = :marks_id_student and m.id.idSt = :marks_id_st")
    SemesterMarks findByStudentAndSubject(@Param("marks_id_student") Long marks_id_student, @Param("marks_id_st") Long marks_id_st);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT number
                    FROM regular_marks
                    WHERE semester_marks_id_st = :semester_marks_id_st
                      AND semester_marks_id_student =
                          (SELECT id FROM students WHERE number_group = :number_group LIMIT 1)
                    ORDER BY number DESC
                    LIMIT 1""")
    Long findLargestNumberRegularMarks(@Param("number_group") Long group, @Param("semester_marks_id_st") Long id_st);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM regular_marks WHERE semester_marks_id_st = :marks_id_st AND semester_marks_id_student = :marks_id_student AND number = :number")
    void deleteMarksNumber(@Param("marks_id_student") Long id_student,@Param("marks_id_st") Long id_st,@Param("number") Long number);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL delete_marks_number(:number, :st, :group)"
    )
    void deleteMarksNumberGroupST(@Param("number") Long number, @Param("group") Long group, @Param("st") Long st);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "insert into marks_marks (marks_id_st, marks_id_student, marks) VALUES (:marks_id_st, :marks_id_student, :mark)"
    )
    void saveMark(@Param("marks_id_student") Long id_student,@Param("marks_id_st") Long id_st,@Param("mark") Double mark);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL insert_students_to_st(:group_add, :st_id_new)"
    )
    void addSemesterMarksForGroup(@Param("group_add") Long group, @Param("st_id_new") Long st_id);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL insert_first_marks_for_group(:group_add, :st_id_new, :date_new)"
    )
    void addFirstRegularMarksForGroup(@Param("group_add") Long group, @Param("st_id_new") Long st_id, @Param("date_new") LocalDate date_new);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL insert_marks_for_group(:group_add, :st_id_new, :number_marks, :date_new)"
    )
    void addRegularMarksForGroup(@Param("group_add") Long group, @Param("st_id_new") Long st_id, @Param("number_marks") Long number_marks, @Param("date_new") LocalDate date_new);
}
