package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.GroupMarksDTO;
import org.spring.diaryBackend.dto.other.STNameSubjectDTO;
import org.spring.diaryBackend.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    @Query("SELECT NEW org.spring.diaryBackend.dto.other.GroupMarksDTO(s.id, s.lastName, s.name, s.patronymic, null) FROM Student s WHERE s.idGroup.id = :idGroup ORDER BY s.lastName, s.name, s.patronymic")
    List<GroupMarksDTO> findBaseInfo(@Param("idGroup") Long idGroup);

    @Query(
    """
            SELECT m.id.idStudent, rm
            FROM SemesterMark m
            LEFT JOIN m.regularMarks rm
            LEFT JOIN rm.idTypeMark tm ON rm.idTypeMark.id = tm.id
            JOIN Student s ON m.id.idStudent = s.id
            WHERE m.id.idSt = :idSt
            AND s.idGroup.id = :idGroup ORDER BY rm.id.number
            """)
    List<Object[]> findAllMarksGroup(@Param("idGroup") Long idGroup,
                                     @Param("idSt") Long idSt);

    @Query(
            value = """
                    SELECT s FROM StudentGroup s WHERE s.numberGroup = :numberGroup AND s.admissionYear = :admissionYear
                    """)
    StudentGroup findStudentGroupByNumberGroupAndAdmissionYear(@Param("numberGroup") Long numberGroup, @Param("admissionYear") Long admissionYear);

    @Query(
            value = """
                    SELECT s FROM StudentGroup s WHERE s.numberGroup = :numberGroup
                    """)
    List<StudentGroup> findStudentGroupByNumberGroup(@Param("numberGroup") Long numberGroup);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "DELETE FROM student_group WHERE id = :id_group"
    )
    void deleteIdGroup(@Param("id_group") Long idGroup);

    @Query(
            value = """
                    SELECT s FROM StudentGroup s WHERE s.id = :idGroup
                    """)
    StudentGroup findStudentGroupByIdGroup(@Param("idGroup") Long idGroup);

    @Query(
            value = """
                    select DISTINCT new org.spring.diaryBackend.dto.other.STNameSubjectDTO(
                        st.id,
                        st.idSubject.id,
                        s.subjectName,
                        t.id,
                        f.lastName,
                        f.name,
                        f.patronymic)
                   FROM SubjectTeacher st
                        JOIN st.teachers t
                        JOIN st.idSubject s
                        JOIN st.groups g
                        JOIN Staff f ON t.id = f.id
                        WHERE s.id = st.idSubject.id AND g.id = :group""")
    List<STNameSubjectDTO> findBySubject(@Param("group") Long group);
}