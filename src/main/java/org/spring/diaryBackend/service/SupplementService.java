package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.FilesDTO;

import java.util.List;

public interface SupplementService {
    List<SupplementDTO> findAllSupplement();

    List<FilesDTO> findAllFilesSupplement(Long id);
}
