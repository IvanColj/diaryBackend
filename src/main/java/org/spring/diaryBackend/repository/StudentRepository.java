package org.spring.diaryBackend.repository;

// import org.spring.diaryBackend.dto.StudentMarksDTO;
import org.spring.diaryBackend.dto.other.StudentMarksDTO;
import org.spring.diaryBackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM student WHERE id_group = :id_group"
    )
    List<Student> findByIdGroup(@Param("id_group") Long idGroup);

    @Query("SELECT NEW org.spring.diaryBackend.dto.other.StudentMarksDTO(s.lastName, s.name, s.patronymic, NULL) FROM Student s WHERE s.id = :idStudent")
    StudentMarksDTO findBaseInfo(@Param("idStudent") Long id);

    @Query("SELECT m.id.idSt, m.regularMarks FROM SemesterMark m WHERE m.idStudent.id = :idStudent")
    List<Object[]> findMarksStudentBySubject(@Param("idStudent") Long idStudent);

    Student findByLoginOrPassword(String login, String password);
}
