package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PathRepository extends JpaRepository<Path, Long> {

    @Query("""
        SELECT p FROM Path p
        WHERE p.type = :type
    """)
    List<Path> findPathsByType(@Param("type") String type);
}