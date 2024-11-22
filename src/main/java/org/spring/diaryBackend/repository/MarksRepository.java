package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Marks;
import org.spring.diaryBackend.model.MarksId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, MarksId> {
    @Query(
            nativeQuery = true,
            value = "select id_st, id_student, certification from marks offset :offset limit :limit")
    List<Marks> findByAllMarks(@Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT m FROM Marks m JOIN FETCH m.marks mm WHERE m.id.id_student = :marks_id_student")
    List<Marks> findByStudentMarks(@Param("marks_id_student") Long marks_id_student);

    @Query("SELECT m FROM Marks m JOIN FETCH m.marks mm WHERE m.id.id_st = :marks_id_st")
    List<Marks> findByObjectMarks(@Param("marks_id_st") Long marks_id_st);

    @Query("SELECT m FROM Marks m JOIN FETCH m.marks mm WHERE m.id.id_student = :marks_id_student and m.id.id_st = :marks_id_st")
    Marks findByStudentAndSubject(@Param("marks_id_student") Long marks_id_student, @Param("marks_id_st") Long marks_id_st);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM marks_marks WHERE ctid IN (SELECT ctid FROM marks_marks WHERE marks_id_st = :marks_id_st AND marks_id_student = :marks_id_student ORDER BY marks_id_st LIMIT 1 OFFSET :offset)")
    void deleteMarksNumber(@Param("marks_id_student") Long id_student,@Param("marks_id_st") Long id_st,@Param("offset") Long offset);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "insert into marks_marks (marks_id_st, marks_id_student, marks) VALUES (:marks_id_st, :marks_id_student, :mark)"
    )
    void saveMark(@Param("marks_id_student") Long id_student,@Param("marks_id_st") Long id_st,@Param("mark") double mark);
}
