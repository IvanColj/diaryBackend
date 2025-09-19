package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.SemesterMarks;
import org.spring.diaryBackend.model.SemesterMarksId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MarksRepository extends JpaRepository<SemesterMarks, SemesterMarksId> {
    @Query(
            nativeQuery = true,
            value = "select id_st, id_student, certification from marks offset :offset limit :limit")
    List<SemesterMarks> findByAllMarks(@Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT m FROM SemesterMarks m JOIN FETCH m.regularMarks mm WHERE m.id.idStudent = :marks_id_student")
    List<SemesterMarks> findByStudentMarks(@Param("marks_id_student") Long marks_id_student);

    @Query("SELECT m FROM SemesterMarks m JOIN FETCH m.regularMarks mm WHERE m.id.idSt = :marks_id_st")
    List<SemesterMarks> findByObjectMarks(@Param("marks_id_st") Long marks_id_st);

    @Query("SELECT m FROM SemesterMarks m JOIN FETCH m.regularMarks mm WHERE m.id.idStudent = :marks_id_student and m.id.idSt = :marks_id_st")
    SemesterMarks findByStudentAndSubject(@Param("marks_id_student") Long marks_id_student, @Param("marks_id_st") Long marks_id_st);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM marks WHERE marks_id_st = :marks_id_st AND marks_id_student = :marks_id_student ctid AND number = :offset)")
    void deleteMarksNumber(@Param("marks_id_student") Long id_student,@Param("marks_id_st") Long id_st,@Param("offset") Long offset);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "insert into marks_marks (marks_id_st, marks_id_student, marks) VALUES (:marks_id_st, :marks_id_student, :mark)"
    )
    void saveMark(@Param("marks_id_student") Long id_student,@Param("marks_id_st") Long id_st,@Param("mark") double mark);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL insert_marks_for_group(:group_add, :st_id_new)"
    )
    void addMarksForGroup(@Param("group_add") Long group, @Param("st_id_new") Long st_id);
}
