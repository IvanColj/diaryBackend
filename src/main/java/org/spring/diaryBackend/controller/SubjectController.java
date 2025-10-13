package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SubjectDTO;
import org.spring.diaryBackend.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subjects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubjectController {
    private SubjectService service;

    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return service.findAllSubject();
    }

    @GetMapping("id/{id}")
    public SubjectDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("save")
    public SubjectDTO save(@RequestBody SubjectDTO subject) {
        return service.saveSubject(subject);
    }

    @PatchMapping("update")
    public SubjectDTO update(@RequestBody SubjectDTO subject) {
        return service.updateSubject(subject);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSubject(id);
    }
}
