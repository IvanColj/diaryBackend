package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.PathDTO;
import org.spring.diaryBackend.repository.PathRepository;
import org.spring.diaryBackend.service.PathService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/paths")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PathController {
    private final PathService pathService;
    private final PathRepository pathRepository;

    @GetMapping()
    public List<PathDTO> findAllPath() {
        return pathService.findAllPath();
    }

    @PutMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
            pathService.uploadFile(file);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        byte[] fileContent = pathService.downloadFile(id);

        String encodedFileName = URLEncoder.encode(pathRepository.findById(id).orElseThrow().getNameFile(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedFileName)
                .body(fileContent);
    }

    @DeleteMapping("delete/{id}")
    public void deleteFile(@PathVariable Long id) {
        pathService.deleteFile(id);
    }
}
