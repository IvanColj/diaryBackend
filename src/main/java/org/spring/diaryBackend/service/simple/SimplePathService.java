package org.spring.diaryBackend.service.simple;

import org.spring.diaryBackend.dto.entity.PathDTO;
import org.spring.diaryBackend.mapper.entity.PathDTOMapper;
import org.spring.diaryBackend.model.Path;
import org.spring.diaryBackend.repository.PathRepository;
import org.spring.diaryBackend.service.PathService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class SimplePathService implements PathService {
    private final PathRepository pathRepository;

    private final PathDTOMapper pathDTOMapper;

    private final RestTemplate restTemplate;

    public SimplePathService(RestTemplateBuilder restTemplateBuilder,
                             PathRepository pathRepository,
                             PathDTOMapper pathDTOMapper) {
        this.pathRepository = pathRepository;
        this.pathDTOMapper = pathDTOMapper;
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Override
    public List<PathDTO> findAllPath() {
        return pathRepository.findAll().stream().map(pathDTOMapper).toList();
    }

    @Override
    public List<PathDTO> findType(String type) {
        return pathRepository.findType(type).stream().map(pathDTOMapper).toList();
    }

    @Value("${file.storage.upload-url}")
    private String uploadUrl;

    @Value("${file.storage.access-url}")
    private String accessUrl;

    @Value("${file.storage.delete-url}")
    private String deleteUrl;

    /**
     * Загружает файл на сервер через PUT запрос
     */
    @Override
    @Transactional
    public void uploadFile(MultipartFile file, Long student, String type) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String fullFileName = fileName + fileExtension;

        ResponseEntity<String> response = restTemplate.exchange(
                uploadUrl + "/" + fullFileName,
                HttpMethod.PUT,
                new HttpEntity<>(file.getBytes(), createHeaders()),
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Upload failed: " + response.getStatusCode());
        }

        Path pathSave = new Path();
        pathSave.setPathToFile(fullFileName);
        pathSave.setIdStudent(student);
        pathSave.setType(type);
        pathSave.setNameFile(file.getOriginalFilename());
        pathRepository.save(pathSave);
    }

    private String getFileExtension(String originalFileName) {
        if (originalFileName == null || !originalFileName.contains(".")) {
            return "";
        }
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return headers;
    }

    /**
     * Скачивает файл с сервера
     */
    @Override
    public byte[] downloadFile(Long id) {
        Path pathGet = pathRepository.findById(id).orElseThrow();
        ResponseEntity<byte[]> response = restTemplate.exchange(
                accessUrl + "/" + pathGet.getPathToFile(),
                HttpMethod.GET,
                null,
                byte[].class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("File not found: " + pathGet.getPathToFile());
        }

        return response.getBody();
    }

    /**
     * Удаляет файл с сервера
     */
    @Override
    @Transactional
    @Modifying
    public void deleteFile(Long id) {
        Path pathDelete = pathRepository.findById(id).orElseThrow();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteUrl + "/" + pathDelete.getPathToFile(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Delete failed: " + response.getStatusCode());
        }

        pathRepository.deleteById(id);
    }
}
