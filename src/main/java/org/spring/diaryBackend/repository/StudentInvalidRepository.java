package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.StudentInvalid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInvalidRepository extends JpaRepository<StudentInvalid, Long> {
}