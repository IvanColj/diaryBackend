package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.StudentOrphan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentOrphanRepository extends JpaRepository<StudentOrphan, Long> {
}
