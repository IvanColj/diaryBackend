package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.Subject;
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
    public List<Subject> getByAllSubjects(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "5") int limit) {
        return service.findByAllSubject(offset, limit);
    }

    @GetMapping("all")
    public List<Subject> getAllSubjects() {
        return service.findAllSubject();
    }

    @GetMapping("id/{id}")
    public Subject getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("save")
    public Subject save(@RequestBody Subject subject) {
        return service.saveSubject(subject);
    }

    @PatchMapping("update")
    public Subject update(@RequestBody Subject subject) {
        return service.updateSubject(subject);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSubject(id);
    }
}
