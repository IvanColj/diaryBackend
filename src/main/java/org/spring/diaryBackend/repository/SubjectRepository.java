package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}