package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.SubjectCourseDTO;
import org.spring.diaryBackend.dto.other.SubjectGroupDTO;
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
            value = "INSERT INTO staff_job_position (id_staff, id_staff_position) VALUES (:id_staff, :id_job)"
    )
    void addStaffJob(@Param("id_staff") Long idStaff, @Param("id_job") Long idJob);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM staff_job_position WHERE id_staff = :id_staff AND id_staff_position = :id_job"
    )
    void deleteStaffJob(@Param("id_staff") Long idStaff, @Param("id_job") Long idJob);

    Staff findByLogin(String login);

    @Query(
            value = """
                    SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.SubjectCourseDTO(
                    s.subjectName,
                    g.course,
                    COUNT(g.id)
                    )
                    FROM SubjectTeacher st
                        JOIN st.teachers t
                        JOIN st.idSubject s
                        JOIN st.groups g
                    WHERE t.id = :idTeacher
                    GROUP BY
                    s.subjectName,
                    g.course
                    """
    )
    List<SubjectCourseDTO> findBySubjectCourse(@Param("idTeacher") Long idTeacher);

    @Query(
            value = """
                    SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.SubjectGroupDTO(
                    g.numberGroup,
                    g.specialty,
                    s.subjectName,
                    COUNT(ss.id)
                    )
                    FROM SubjectTeacher st
                        JOIN st.teachers t
                        JOIN st.idSubject s
                        JOIN st.groups g
                        JOIN g.students ss
                    WHERE t.id = :idTeacher
                    GROUP BY
                    g.numberGroup,
                    g.specialty,
                    s.subjectName
                    """
    )
    List<SubjectGroupDTO> findByGroup(@Param("idTeacher") Long idTeacher);
}
