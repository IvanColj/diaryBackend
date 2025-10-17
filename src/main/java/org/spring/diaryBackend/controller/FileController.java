package org.spring.diaryBackend.controller;

import org.spring.diaryBackend.service.simple.RemoteFileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final RemoteFileStorageService remoteFileService;

    public FileController(RemoteFileStorageService remoteFileService) {
        this.remoteFileService = remoteFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<RemoteFileStorageService.FileResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = remoteFileService.uploadFile(file);

            RemoteFileStorageService.FileResponse response =
                    new RemoteFileStorageService.FileResponse(
                            extractFileNameFromUrl(fileUrl),
                            file.getContentType(),
                            file.getSize(),
                            fileUrl
                    );

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            byte[] fileContent = remoteFileService.downloadFile(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileName) {
        try {
            remoteFileService.deleteFile(fileName);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private String extractFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}