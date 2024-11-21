package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query(
            nativeQuery = true,
            value = "select * from students offset :offset limit :limit")
    List<Teacher> findByAllTeacher(@Param("offset") int offset, @Param("limit") int limit);
    Teacher findByLoginOrPassword(@Param("login") String login, String password);
}
