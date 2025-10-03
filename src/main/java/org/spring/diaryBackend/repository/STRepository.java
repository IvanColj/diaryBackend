package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.STGroupDTO;
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
            value = "select * from subject_teacher offset :offset limit :limit")
    List<SubjectTeacher> findByAllSubjectTeacher(@Param("offset") int offset, @Param("limit") int limit);

    @Query(
            nativeQuery = true,
            value = "select * from subject_teacher where id_teacher = :teacherId")
    List<SubjectTeacher> findByTeacher(@Param("teacherId") Long teacherId);

    @Query("""
            select DISTINCT new org.spring.diaryBackend.dto.STGroupDTO(st.idTeacher, st.idSubject , s.subjectName, g)
                FROM Subject s, SubjectTeacher st JOIN st.groups g
                WHERE s.id = st.idSubject and st.idTeacher = :idTeacher
            """)
    List<STGroupDTO> findBySTGroup(@Param("idTeacher") Long idTeacher);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "insert into subject_teacher_groups (subject_teacher_id, groups) VALUES (:subject_teacher_id_st, :group)"
    )
    void addingSTGroup(@Param("subject_teacher_id_st") Long id_st, @Param("group") Long group);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM subject_teacher_groups WHERE subject_teacher_id = :subject_teacher_id_st and groups = :group")
    void deleteSTGroup(@Param("subject_teacher_id_st") Long id_st, @Param("group") Long group);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "update subject_teacher_groups set groups = :newGroup WHERE subject_teacher_id = :subject_teacher_id_st and groups = :group")
    void updateSTGroup(@Param("subject_teacher_id_st") Long id_st, @Param("group") Long group, @Param("newGroup") Long newGroup);
}
