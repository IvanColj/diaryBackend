package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.FilesDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SupplementService {
    List<SupplementDTO> findAllSupplement();

    List<FilesDTO> findAllFilesSupplement(Long id);

    void addFileSupplement(Long id, MultipartFile file) throws IOException;

    void deleteFileSupplement(Long idSupplement, Long idFile);

    SupplementDTO save();

    void update(Long id, String comment);

    void delete(Long id);
}
