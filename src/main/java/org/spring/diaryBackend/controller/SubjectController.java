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
    private SubjectService subjectService;

    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectService.findAllSubject();
    }

    @GetMapping("id/{id}")
    public SubjectDTO getById(@PathVariable Long id) {
        return subjectService.findById(id);
    }

    @PostMapping("save")
    public SubjectDTO save(@RequestBody SubjectDTO subject) {
        return subjectService.saveSubject(subject);
    }

    @PatchMapping("update")
    public SubjectDTO update(@RequestBody SubjectDTO subject) {
        return subjectService.updateSubject(subject);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }
}