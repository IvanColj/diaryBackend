package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.ChangeDTO;
import org.spring.diaryBackend.dto.other.ChangeInfoDTO;

import java.util.List;

public interface ChangeService {
    List<ChangeDTO> findAllChange();

    List<ChangeInfoDTO> findByAllChangeMark(Long idSt, Long idStudent, Long numberMark);

    ChangeDTO addSupplement(Long id);
}
