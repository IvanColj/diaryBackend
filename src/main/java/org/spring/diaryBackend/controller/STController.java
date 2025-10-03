package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.STGroupsDTO;
import org.spring.diaryBackend.model.SubjectTeacher;
import org.spring.diaryBackend.service.STService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/st")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class STController {
    private final STService service;

    @GetMapping
    public List<SubjectTeacher> getSubjectTeachers(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "5") int limit) {
        return service.findByAllSubjectTeacher(offset, limit);
    }

    @GetMapping("all")
    public List<SubjectTeacher> getAllSubjectTeachers() {
        return service.findAllSubjectTeacher();
    }

    @GetMapping("teacher/{id}")
    public List<SubjectTeacher> getTeachers(@PathVariable Long id) {
        return service.findByTeacher(id);
    }

    @GetMapping("teacherGroups/{id}")
    public List<STGroupsDTO> getSTGroups(@PathVariable Long id) {
        return service.findBySTGroups(id);
    }

    @GetMapping("id/{id}")
    public SubjectTeacher getSubjectTeacher(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("save")
    public SubjectTeacher saveSubjectTeacher(@RequestBody SubjectTeacher subjectTeacher) {
        return service.saveSubjectTeacher(subjectTeacher);
    }

    @PostMapping("add/id/{id}/group/{group}")
    public void addGroup(@PathVariable Long id, @PathVariable Long group) {
        service.addingSTGroup(id, group);
    }

    @PatchMapping("update/id/{id}/group/{group}/newGroup/{newGroup}")
    public void updateGroup(@PathVariable Long id, @PathVariable Long group, @PathVariable Long newGroup) {
        service.updateSTGroup(id, group, newGroup);
    }

    @PatchMapping("update")
    public SubjectTeacher updateSubjectTeacher(@RequestBody SubjectTeacher subjectTeacher) {
        return service.updateSubjectTeacher(subjectTeacher);
    }

    @DeleteMapping("delete/id/{id}")
    public void deleteSubjectTeacher(@PathVariable Long id) {
        service.deleteSubjectTeacher(id);
    }

    @DeleteMapping("delete/id/{id}/group/{group}")
    public void deleteGroup(@PathVariable Long id, @PathVariable Long group) {
        service.deleteSTGroup(id, group);
    }
}
