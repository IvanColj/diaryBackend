package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.ChangeDTO;
import org.spring.diaryBackend.dto.other.ChangeInfoDTO;
import org.spring.diaryBackend.model.Change;

import java.util.List;

public interface ChangeService {
    List<ChangeDTO> findAllChange();

    List<ChangeInfoDTO> findByAllChangeMark(Long idSt, Long idStudent, Long numberMark);

    ChangeDTO addSupplement(Long id);

    Change save(Long idSt, Long idStudent, Long number);
}
