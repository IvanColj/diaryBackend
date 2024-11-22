package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.StudentMarksDTO;
import org.spring.diaryBackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(
            nativeQuery = true,
            value = "select * from students offset :offset limit :limit")
    List<Student> findByAllStudent(@Param("offset") int offset, @Param("limit") int limit);

    @Query(
            nativeQuery = true,
            value = "select * from students where number_group = :numberGroup"
    )
    List<Student> findByNumberGroup(@Param("numberGroup") Long numberGroup);

    @Query("select new org.spring.diaryBackend.dto.StudentMarksDTO(s.name, s.lastName, null) from Student s where s.id = :studentId")
    StudentMarksDTO findBaseInfo(@Param("studentId") Long id);

    @Query("select m.id.id_st, m.marks as marks from Marks m where m.id.id_student = :studentId")
    List<Object[]> findMarksStudentBySubject(@Param("studentId") Long id);

    Student findByLoginOrPassword(@Param("login") String login, String password);
}
