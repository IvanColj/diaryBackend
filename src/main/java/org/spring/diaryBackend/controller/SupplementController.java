package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.FilesDTO;
import org.spring.diaryBackend.service.SupplementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/supplement")
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
}
