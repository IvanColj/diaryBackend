package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SubjectTeacherDTO;
import org.spring.diaryBackend.dto.other.STGroupsDTO;
import org.spring.diaryBackend.dto.other.STNumberMarkTypeMarkDTO;
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
    public List<SubjectTeacherDTO> getAllSubjectTeachers() {
        return service.findAllSubjectTeacher();
    }

    @GetMapping("teacher/{id}")
    public List<SubjectTeacherDTO> getTeachers(@PathVariable Long id) {
        return service.findByTeacher(id);
    }

    @GetMapping("teacherGroups/{id}")
    public List<STGroupsDTO> getSTGroups(@PathVariable Long id) {
        return service.findBySTGroups(id);
    }

    @GetMapping("id/{id}")
    public SubjectTeacherDTO getSubjectTeacher(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("typeMark/id/{id}")
    public List<STNumberMarkTypeMarkDTO> findByStNumberMarkType(@PathVariable Long id) {
        return service.findByStNumberMarkType(id);
    }

    @PostMapping("save")
    public SubjectTeacherDTO saveSubjectTeacher(@RequestBody SubjectTeacherDTO subjectTeacher) {
        return service.saveSubjectTeacher(subjectTeacher);
    }

    @PostMapping("add/id/{id}/group/{group}")
    public void addGroup(@PathVariable Long id, @PathVariable Long group) {
        service.addingSTGroup(id, group);
    }

    @PostMapping("add/id/{id}/teacher/{teacher}")
    public void addTeacher(@PathVariable Long id, @PathVariable Long teacher) {
        service.addingTeacher(id, teacher);
    }

    @PatchMapping("update")
    public SubjectTeacherDTO updateSubjectTeacher(@RequestBody SubjectTeacherDTO subjectTeacher) {
        return service.updateSubjectTeacher(subjectTeacher);
    }

    @DeleteMapping("delete/{id}")
    public void deleteSubjectTeacher(@PathVariable Long id) {
        service.deleteSubjectTeacher(id);
    }

    @DeleteMapping("delete/id/{id}/group/{group}")
    public void deleteGroup(@PathVariable Long id, @PathVariable Long group) {
        service.deleteSTGroup(id, group);
    }

    @DeleteMapping("delete/id/{id}/teacher/{teacher}")
    public void deleteTeacher(@PathVariable Long id, @PathVariable Long teacher) {
        service.deleteSTTeacher(id, teacher);
    }
}
