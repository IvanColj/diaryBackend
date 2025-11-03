package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.FilesDTO;
import org.spring.diaryBackend.service.SupplementService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/supplements")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplementController {
    private final SupplementService supplementService;

    @GetMapping
    public List<SupplementDTO> findAllSupplement() {
        return supplementService.findAllSupplement();
    }

    @GetMapping("files/id/{id}")
    public List<FilesDTO> findAllFiles(@PathVariable Long id) {
        return supplementService.findAllFilesSupplement(id);
    }

    @PostMapping("add/files/id/{id}")
    public void findAllFiles(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) throws IOException {
        supplementService.addFileSupplement(id, file);
    }

    @DeleteMapping("delete/files/{idFile}/id/{id}")
    public void deleteFiles(@PathVariable("idFile") Long idFile, @PathVariable("id") Long idSupplement) {
        supplementService.deleteFileSupplement(idSupplement, idFile);
    }

    @DeleteMapping("delete/id/{id}")
    public void delete(@PathVariable Long id) {
        supplementService.delete(id);
    }
}
