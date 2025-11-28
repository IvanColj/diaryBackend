package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.GroupMarksDTO;
import org.spring.diaryBackend.dto.other.STInfoDTO;
import org.spring.diaryBackend.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    @Query("""
        SELECT NEW org.spring.diaryBackend.dto.other.GroupMarksDTO(
            s.id, s.lastName, s.name, s.patronymic, null
        )
        FROM Student s
        WHERE s.idGroup.id = :idGroup
        ORDER BY s.lastName, s.name, s.patronymic
    """)
    List<GroupMarksDTO> findStudentsFIO(@Param("idGroup") Long idGroup);

    @Query("""
        SELECT s FROM StudentGroup s
        WHERE s.numberGroup = :numberGroup
            AND s.admissionYear = :admissionYear
    """)
    StudentGroup findGroupByNumberAndAdmissionYear(
            @Param("numberGroup") Long numberGroup,
            @Param("admissionYear") Long admissionYear
    );

    @Query("""
        SELECT s FROM StudentGroup s
        WHERE s.numberGroup = :numberGroup
    """)
    List<StudentGroup> findGroupByNumber(@Param("numberGroup") Long numberGroup);

    @Modifying
    @Query(nativeQuery = true,
            value = """
            DELETE FROM student_group
            WHERE id = :id_group
           """)
    void deleteGroupById(@Param("id_group") Long idGroup);

    @Query("""
        SELECT s FROM StudentGroup s
        WHERE s.id = :idGroup
    """)
    StudentGroup findGroupById(@Param("idGroup") Long idGroup);

    @Query("""
        SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.STInfoDTO(
            st.id,
            st.idSubject.id,
            s.subjectName,
            t.id,
            f.lastName,
            f.name,
            f.patronymic
        )
        FROM SubjectTeacher st
        JOIN st.teachers t
        JOIN st.idSubject s
        JOIN st.groups g
        JOIN Staff f ON t.id = f.id
        WHERE s.id = st.idSubject.id
            AND g.id = :group
    """)
    List<STInfoDTO> findGroupSubjects(@Param("group") Long group);
}