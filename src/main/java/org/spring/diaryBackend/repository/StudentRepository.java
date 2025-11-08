package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.STNameSubjectDTO;
import org.spring.diaryBackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM student WHERE id_group = :id_group ORDER BY last_name, name, patronymic"
    )
    List<Student> findByIdGroup(@Param("id_group") Long idGroup);

    @Query("SELECT sm.id.idSt, rm, sm.certification " +
            "FROM SemesterMark sm " +
            "LEFT JOIN RegularMark rm ON sm.id.idSt = rm.semesterMark.idSt.id AND sm.id.idStudent = rm.semesterMark.idStudent.id " +
            "WHERE sm.id.idStudent = :idStudent")
    List<Object[]> findMarksStudentBySubject(@Param("idStudent") Long idStudent);

    @Query("""
                SELECT DISTINCT new org.spring.diaryBackend.dto.other.STNameSubjectDTO(
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
                JOIN Staff f ON t.id = f.id
                JOIN SemesterMark sm ON st.id = sm.idSt.id
                WHERE sm.idStudent.id = :idStudent
                AND (EXISTS (
                    SELECT ss.id
                    FROM Subgroup sub
                    JOIN sub.students ss
                    WHERE sub.idSt.id = st.id
                    AND sub.idTeacher.id = t.id
                    AND ss.id = :idStudent
                ) OR NOT EXISTS (
                    SELECT sub2
                    FROM Subgroup sub2
                    WHERE sub2.idSt.id = st.id
                    AND sub2.idTeacher.id = t.id
                ))
            """)
    List<STNameSubjectDTO> findSubjectsByStudent(@Param("idStudent") Long idStudent);

    Student findByLoginOrPassword(String login, String password);
}
