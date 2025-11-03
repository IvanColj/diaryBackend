package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.dto.other.FilesDTO;
import org.spring.diaryBackend.model.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

    @Query("""
        SELECT NEW org.spring.diaryBackend.dto.other.FilesDTO(p.id, p.nameFile) FROM Supplement s JOIN s.paths p WHERE s.id = :id
""")
    List<FilesDTO> findAllFilesSupplement(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "INSERT INTO file_path (id_supplement, id_path) VALUES (:id_supplement, :id_file)"
    )
    void addingFileSupplement(@Param("id_supplement") Long idSupplement, @Param("id_file") Long idFile);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM WHERE id_supplement = :id_supplement AND id_path = :id_file)"
    )
    void deleteFileSupplement(@Param("id_supplement") Long idSupplement, @Param("id_file") Long idFile);
}
