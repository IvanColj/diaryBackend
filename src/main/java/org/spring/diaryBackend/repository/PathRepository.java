package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathRepository extends JpaRepository<Path, Long> {
}
