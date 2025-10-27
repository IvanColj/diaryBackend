package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.STGroupDTO;
import org.spring.diaryBackend.dto.other.STNumberMarkTypeMarkDTO;
import org.spring.diaryBackend.model.SubjectTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface STRepository extends JpaRepository<SubjectTeacher, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM subject_teacher st JOIN teachers_st ts ON st.id = ts.id_st where ts.id_teacher = :id_teacher")
    List<SubjectTeacher> findByTeacher(@Param("id_teacher") Long idTeacher);

    @Query("""
            SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.STGroupDTO(
                t.id,
                s.id,
                s.subjectName,
                g.id
            )
            FROM SubjectTeacher st
                JOIN st.teachers t
                JOIN st.idSubject s
                JOIN st.groups g
            WHERE t.id = :idTeacher
            """)
    List<STGroupDTO> findBySTGroup(@Param("idTeacher") Long idTeacher);


    @Query("""
                SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.STNumberMarkTypeMarkDTO(
                            rm.id.number, tm.name, tm.weight
                ) FROM RegularMark rm JOIN TypeMark tm ON rm.idTypeMark.id = tm.id
                WHERE rm.id.semesterMarkIdSt = :idSt ORDER BY tm.weight, tm.name, rm.id.number
            """)
    List<STNumberMarkTypeMarkDTO> findByStNumberMarkTypeMark(@Param("idSt") Long idSt);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "INSERT INTO subject_teacher_groups (id_st, id_group) VALUES (:id_st, :id_group)"
    )
    void addingSTGroup(@Param("id_st") Long idSt, @Param("id_group") Long idGroup);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "INSERT INTO teachers_st (id_st, id_teacher) VALUES (:id_st, :id_teacher)"
    )
    void addingTeacher(@Param("id_st") Long idSt, @Param("id_teacher") Long idTeacher);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "CALL insert_new_teacher_in_subgroup(:id_st, :id_group, :id_teacher_old, :id_teacher_new)"
    )
    void addingTwoGroupToSubgroup(@Param("id_st") Long idSt, @Param("id_group") Long idGroup, @Param("id_teacher_old") Long idTeacherOld, @Param("id_teacher_new") Long idTeacherNew);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM subject_teacher_groups WHERE id_st = :id_st AND id_group = :id_group")
    void deleteSTGroup(@Param("id_st") Long idSt, @Param("id_group") Long idGroup);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM teachers_st WHERE id_st = :id_st AND id_teacher = :id_teacher")
    void deleteSTTeacher(@Param("id_st") Long idSt, @Param("id_teacher") Long idTeacher);
}
