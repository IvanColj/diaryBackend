package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(
            nativeQuery = true,
            value = "select id_subject, subject_name from Subject offset :offset limit :limit"
    )
    List<Subject> findByAllSubject(int offset, int limit);
}
