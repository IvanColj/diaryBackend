package org.spring.diaryBackend.service.simple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class RemoteFileStorageService {

    @Value("${file.storage.upload-url}")
    private String uploadUrl;

    @Value("${file.storage.access-url}")
    private String accessUrl;

    @Value("${file.storage.delete-url}")
    private String deleteUrl;

    private final RestTemplate restTemplate;

    public RemoteFileStorageService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }

    /**
     * Загружает файл на сервер через PUT запрос
     */
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" +
                    file.getOriginalFilename().replace(" ", "_");

            String fullUploadUrl = uploadUrl + "/" + fileName;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            byte[] fileBytes = file.getBytes();
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileBytes, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    fullUploadUrl,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return accessUrl + "/" + fileName;
            } else {
                throw new RuntimeException("Ошибка загрузки файла: " + response.getStatusCode());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка чтения файла", ex);
        }
    }

    /**
     * Скачивает файл с сервера
     */
    public byte[] downloadFile(String fileName) {
        try {
            String fileUrl = accessUrl + "/" + fileName;
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    fileUrl,
                    HttpMethod.GET,
                    null,
                    byte[].class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Файл не найден: " + fileName);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка загрузки файла: " + fileName, ex);
        }
    }

    /**
     * Удаляет файл с сервера
     */
    public void deleteFile(String fileName) {
        try {
            String fullDeleteUrl = deleteUrl + "/" + fileName;

            ResponseEntity<Void> response = restTemplate.exchange(
                    fullDeleteUrl,
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Ошибка удаления файла: " + response.getStatusCode());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка удаления файла: " + fileName, ex);
        }
    }

    public static record FileResponse(
            String fileName,
            String fileType,
            long size,
            String downloadUrl
    ) {}
}