package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.ChangeDTO;

import java.util.List;

public interface ChangeService {
    List<ChangeDTO> findAllChange();

    List<ChangeDTO> findByAllChangeMark(Long idSt, Long idStudent, Long numberMark);
}
