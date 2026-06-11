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
        SELECT sg
        FROM StudentGroup sg
        WHERE sg.departmentHead.id = :idStaff
        ORDER BY sg.numberGroup
    """)
    List<StudentGroup> findGroupsDepartment(@Param("idStaff") Long idStaff);

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

    @Query("""
        SELECT DISTINCT NEW org.spring.diaryBackend.dto.other.STInfoDTO(
            st.id,
            s.id,
            s.subjectName,
            t.id,
            t.lastName,
            t.name,
            t.patronymic
        )
        FROM GroupCertificationSchedule gcs
        JOIN gcs.subjectTeacher st
        JOIN st.idSubject s
        JOIN st.teachers t
        WHERE gcs.studentGroup.id = :group
    """)
    List<STInfoDTO> findGroupCertificationSubjects(@Param("group") Long group);

    @Query(value = """
        WITH
        student_info AS (
            SELECT
                s.id_group,
                COUNT(*) as total_students,
                STRING_AGG(CASE WHEN s.is_leader = true THEN s.last_name || ' ' || s.name || ' ' || s.patronymic END, ', ' ORDER BY s.last_name) as leaders
            FROM student s
            GROUP BY s.id_group
        ),
        social_info AS (
            SELECT
                s.id_group,
                COUNT(*) as total_social
            FROM student_social_category ssc
            JOIN student s ON ssc.id_student = s.id
            GROUP BY s.id_group
        ),
        grade_info AS (
            SELECT
                s.id_group,
                AVG(sm.certification) as avg_grade
            FROM semester_mark sm
            JOIN student s ON sm.id_student = s.id
            GROUP BY s.id_group
        ),
        attendance_info AS (
            SELECT
                s.id_group,
                (COUNT(*) FILTER (WHERE a.status = 'п') * 100.0 / NULLIF(COUNT(*), 0)) as attendance_pct
            FROM attendance a
            JOIN student s ON a.id_student = s.id
            GROUP BY s.id_group
        )
        SELECT
            sg.number_group,
            sg.course,
            sg.specialty,
            si.total_students,
            soc.total_social,
            si.leaders,
            cur.last_name || ' ' || cur.name || ' ' || cur.patronymic as curator_fio,
            gi.avg_grade,
            ai.attendance_pct
        FROM student_group sg
        JOIN staff cur ON sg.id_curator = cur.id
        LEFT JOIN student_info si ON sg.id = si.id_group
        LEFT JOIN social_info soc ON sg.id = soc.id_group
        LEFT JOIN grade_info gi ON sg.id = gi.id_group
        LEFT JOIN attendance_info ai ON sg.id = ai.id_group
        WHERE sg.id_curator = :curatorId
        """, nativeQuery = true)
    List<Object[]> getCuratorDetailedStats(@Param("curatorId") Long curatorId);

    @Query(value = """
        WITH
        student_info AS (
            SELECT
                COUNT(*) as total_students,
                STRING_AGG(CASE WHEN s.is_leader = true THEN s.last_name || ' ' || s.name || ' ' || s.patronymic END, ', ' ORDER BY s.last_name) as leaders
            FROM student s
            WHERE s.id_group = :groupId
        ),
        social_info AS (
            SELECT COUNT(*) as total_social
            FROM student_social_category ssc
            JOIN student s ON ssc.id_student = s.id
            WHERE s.id_group = :groupId
        ),
        grade_info AS (
            SELECT AVG(sm.certification) as avg_grade
            FROM semester_mark sm
            JOIN student s ON sm.id_student = s.id
            WHERE s.id_group = :groupId
        ),
        attendance_info AS (
            SELECT
                (COUNT(*) FILTER (WHERE a.status = 'п') * 100.0 / NULLIF(COUNT(*), 0)) as attendance_pct
            FROM attendance a
            JOIN student s ON a.id_student = s.id
            WHERE s.id_group = :groupId
        )
        SELECT
            sg.number_group,
            sg.course,
            sg.specialty,
            si.total_students,
            soc.total_social,
            si.leaders,
            cur.last_name || ' ' || cur.name || ' ' || cur.patronymic as curator_fio,
            gi.avg_grade,
            ai.attendance_pct
        FROM student_group sg
        JOIN staff cur ON sg.id_curator = cur.id
        CROSS JOIN student_info si
        CROSS JOIN social_info soc
        CROSS JOIN grade_info gi
        CROSS JOIN attendance_info ai
        WHERE sg.id = :groupId
        """, nativeQuery = true)
    List<Object[]> getDetailedStats(@Param("groupId") Long groupId);
}
