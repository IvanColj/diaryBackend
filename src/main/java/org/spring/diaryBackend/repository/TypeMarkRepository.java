package org.spring.diaryBackend.repository;

import org.spring.diaryBackend.model.TypeMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeMarkRepository extends JpaRepository<TypeMark, Long> {

    @Query(value = "SELECT tm FROM TypeMark tm WHERE tm.idSt.id = :idSt")
    List<TypeMark> findBySt(@Param("idSt") Long idSt);
}
