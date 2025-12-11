package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.StaffPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffPositionRepository extends JpaRepository<StaffPosition, Long> {
}