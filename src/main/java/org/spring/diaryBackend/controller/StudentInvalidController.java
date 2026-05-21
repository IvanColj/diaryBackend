package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentInvalidDTO;
import org.spring.diaryBackend.dto.other.ExportStudentInvalidDTO;
import org.spring.diaryBackend.service.StudentInvalidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/invalids")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentInvalidController {
    private final StudentInvalidService service;

    @GetMapping("all")
    public List<ExportStudentInvalidDTO> getAll() {
        return service.findAll();
    }

    @PostMapping("save")
    public StudentInvalidDTO save(@RequestBody StudentInvalidDTO dto) {
        return service.save(dto);
    }

    @PatchMapping("update")
    public StudentInvalidDTO update(@RequestBody StudentInvalidDTO dto) {
        return service.update(dto);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}