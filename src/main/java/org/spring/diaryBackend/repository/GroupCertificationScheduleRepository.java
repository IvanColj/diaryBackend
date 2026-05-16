package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.getCurrentCertificationGroupDTO;
import org.spring.diaryBackend.model.GroupCertificationSchedule;
import org.spring.diaryBackend.model.GroupCertificationScheduleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupCertificationScheduleRepository extends JpaRepository<GroupCertificationSchedule, GroupCertificationScheduleId> {
    @Query("""
    SELECT gcs.certificationType
    FROM GroupCertificationSchedule gcs
    JOIN gcs.studentGroup sg
    WHERE gcs.id.idSt = :idSt
      AND gcs.id.idGroup = :groupId
      AND gcs.id.semester = sg.currentSemester
""")
    Optional<String> findCertificationType(@Param("idSt") Long idSt,
                                           @Param("groupId") Long groupId);

    @Query("""
    SELECT st.id, st.idSubject.subjectName, gcs.certificationType
    FROM GroupCertificationSchedule gcs
    JOIN SubjectTeacher st ON gcs.id.idSt = st.id
    JOIN gcs.studentGroup sg
    WHERE gcs.id.idGroup = :groupId
      AND gcs.id.semester = sg.currentSemester
""")
    List<getCurrentCertificationGroupDTO> findCurrentCertificationGroup(@Param("groupId") Long groupId);

    @Query("""
    SELECT st.id, st.idSubject.subjectName, gcs.certificationType, gcs.id.semester
    FROM GroupCertificationSchedule gcs
    JOIN SubjectTeacher st ON gcs.id.idSt = st.id
    JOIN gcs.studentGroup sg
    WHERE gcs.id.idGroup = :groupId
""")
    List<getCurrentCertificationGroupDTO> findAllCertificationGroup(@Param("groupId") Long groupId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = """
            INSERT INTO group_certification_schedule (id_st, id_group, semester, certification_type)
            VALUES (:id_st, :id_group, :semester, :certification_type)
            """)
    void saveCertification(@Param("id_st") Long idSt,
              @Param("id_group") Long idGroup,
              @Param("semester") Long semester,
              @Param("certification_type") String certificationType);
}