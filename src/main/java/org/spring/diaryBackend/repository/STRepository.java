package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.STGroupDTO;
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
            value = "SELECT * FROM subject_teacher WHERE id_teacher = :id_teacher")
    List<SubjectTeacher> findByTeacher(@Param("id_teacher") Long idTeacher);

    @Query("""
            SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.STGroupDTO(st.idTeacher.id, st.idSubject.id, s.subjectName, g.id)
                FROM Subject s, SubjectTeacher st JOIN st.groups g
                WHERE s.id = st.idSubject.id AND st.idTeacher.id = :idTeacher
            """)
    List<STGroupDTO> findBySTGroup(@Param("idTeacher") Long idTeacher);

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
            value = "DELETE FROM subject_teacher_groups WHERE id_st = :id_st AND id_group = :id_group")
    void deleteSTGroup(@Param("id_st") Long idSt, @Param("id_group") Long idGroup);
}
