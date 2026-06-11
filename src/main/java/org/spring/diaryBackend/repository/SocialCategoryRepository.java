package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.SocialCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialCategoryRepository extends JpaRepository<SocialCategory, Long> {
}