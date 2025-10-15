package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Staff;
import org.spring.diaryBackend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Staff findByLoginOrPassword(@Param("login") String login, String password);

    @Query("""
    SELECT s
    FROM SubjectTeacher st
        JOIN st.idSubject s
        JOIN st.teachers t
    WHERE t.id = :id
    """)
    List<Subject> findByAllSubject(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "INSERT INTO staff_job_position (id_staff, id_staff_position) VALUES (:idStaff, :idJob)"
    )
    void addStaffJob(@Param("idStaff") Long idStaff, @Param("idJob") Long idJob);
}
