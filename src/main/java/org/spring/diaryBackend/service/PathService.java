package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.PathDTO;
import org.spring.diaryBackend.model.Path;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PathService {
    List<PathDTO> findAllPath();
    List<PathDTO> findType(String type);

    byte[] downloadFile(Long id);
    Path uploadFile(MultipartFile file, Long student, String type) throws IOException;
    void deleteFile(Long id);
}
