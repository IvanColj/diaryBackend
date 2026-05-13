package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.GroupCertificationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupCertificationScheduleRepository extends JpaRepository<GroupCertificationSchedule, Long> {
    @Query("""
        SELECT gcs.certificationType FROM GroupCertificationSchedule gcs
        WHERE gcs.studentGroup.id = :groupId
        AND gcs.studentGroup.currentSemester = :semester
        AND gcs.semester = :semester
        AND gcs.subjectTeacher.id = :idSt
    """)
    Optional<String> findCertificationType(@Param("idSt") Long idSt,
                                           @Param("groupId") Long groupId,
                                           @Param("semester") Long semester);
}