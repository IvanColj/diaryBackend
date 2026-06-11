package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentOrphanDTO;
import org.spring.diaryBackend.dto.other.ExportStudentOrphanDTO;
import org.spring.diaryBackend.service.StudentOrphanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orphans")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentOrphanController {
    private final StudentOrphanService service;

    @GetMapping("all")
    public List<ExportStudentOrphanDTO> getAll() {
        return service.findAll();
    }

    @PostMapping("save")
    public StudentOrphanDTO save(@RequestBody StudentOrphanDTO dto) {
        return service.save(dto);
    }

    @PatchMapping("update")
    public StudentOrphanDTO update(@RequestBody StudentOrphanDTO dto) {
        return service.update(dto);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
