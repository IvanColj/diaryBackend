package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.FilesDTO;
import org.spring.diaryBackend.model.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

    @Query("""
        SELECT NEW org.spring.diaryBackend.dto.other.FilesDTO(p.id, p.nameFile) FROM Supplement s JOIN s.paths p WHERE s.id = :id
""")
    List<FilesDTO> findAllFilesSupplement(@Param("id") Long id);
}
