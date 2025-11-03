package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.TypeMarkDTO;

import java.util.List;

public interface TypeMarkService {
    List<TypeMarkDTO> findAll();

    List<TypeMarkDTO> findBySt(Long idSt);

    void save(TypeMarkDTO typeMarkDTO);

    void update(TypeMarkDTO typeMarkDTO);

    void delete(Long id);
}
