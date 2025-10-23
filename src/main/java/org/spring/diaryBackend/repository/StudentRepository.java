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

    @Query("SELECT m.id.idSt, m.regularMarks FROM SemesterMark m WHERE m.idStudent.id = :idStudent")
    List<Object[]> findMarksStudentBySubject(@Param("idStudent") Long idStudent);

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
                         JOIN SemesterMark sm ON st.id = sm.idSt.id
                         JOIN st.teachers t
                         JOIN st.idSubject s
                         JOIN Staff f ON t.id = f.id
                         WHERE s.id = st.idSubject.id AND sm.idStudent.id = :idStudent""")
    List<STNameSubjectDTO> findBySubject(@Param("idStudent") Long idStudent);

    Student findByLoginOrPassword(String login, String password);
}
