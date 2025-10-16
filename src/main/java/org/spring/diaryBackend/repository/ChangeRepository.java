package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Change;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeRepository extends JpaRepository<Change, Long> {

}
