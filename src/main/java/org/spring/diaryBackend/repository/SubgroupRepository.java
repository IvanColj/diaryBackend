package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.Subgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubgroupRepository extends JpaRepository<Subgroup, Long> {

    @Query(
            """
            SELECT s FROM Subgroup s WHERE s.idSt.id = :idSt AND s.idTeacher.id = :idTeacher
            """
    )
    Subgroup findByIdStAndIdTeacher(@Param("idSt") Long idSt, @Param("idTeacher") Long idTeacher);
}
