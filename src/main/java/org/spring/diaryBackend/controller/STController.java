package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SubjectTeacherDTO;
import org.spring.diaryBackend.dto.other.STMarkTypesDTO;
import org.spring.diaryBackend.dto.other.SubjectGroupsDTO;
import org.spring.diaryBackend.service.STService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/st")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class STController {
    private final STService stService;

    @GetMapping
    public List<SubjectTeacherDTO> getAllSubjectTeachers() {
        return stService.findAllSubjectTeacher();
    }

    @GetMapping("teacher/{id}")
    public List<SubjectTeacherDTO> getTeachers(@PathVariable Long id) {
        return stService.findByTeacher(id);
    }

    @GetMapping("teacherGroups/{id}")
    public List<SubjectGroupsDTO> getSTGroups(@PathVariable Long id) {
        return stService.findBySTGroups(id);
    }

    @GetMapping("id/{id}")
    public SubjectTeacherDTO getSubjectTeacher(@PathVariable Long id) {
        return stService.findById(id);
    }

    @GetMapping("typeMark/id/{id}")
    public List<STMarkTypesDTO> findByStNumberMarkType(@PathVariable Long id) {
        return stService.findByStNumberMarkType(id);
    }

    @PostMapping("save")
    public SubjectTeacherDTO saveSubjectTeacher(@RequestBody SubjectTeacherDTO subjectTeacher) {
        return stService.saveSubjectTeacher(subjectTeacher);
    }

    @PostMapping("add/id/{id}/group/{group}")
    public void addGroup(@PathVariable Long id,
                         @PathVariable Long group) {
        stService.addingSTGroup(id, group);
    }

    @PostMapping("add/id/{id}/teacher/{teacher}")
    public void addTeacher(@PathVariable Long id,
                           @PathVariable Long teacher) {
        stService.addingTeacher(id, teacher);
    }

    @PatchMapping("update")
    public SubjectTeacherDTO updateSubjectTeacher(@RequestBody SubjectTeacherDTO subjectTeacher) {
        return stService.updateSubjectTeacher(subjectTeacher);
    }

    @DeleteMapping("delete/{id}")
    public void deleteSubjectTeacher(@PathVariable Long id) {
        stService.deleteSubjectTeacher(id);
    }

    @DeleteMapping("delete/id/{id}/group/{group}")
    public void deleteGroup(@PathVariable Long id,
                            @PathVariable Long group) {
        stService.deleteSTGroup(id, group);
    }

    @DeleteMapping("delete/id/{id}/teacher/{teacher}")
    public void deleteTeacher(@PathVariable Long id,
                              @PathVariable Long teacher) {
        stService.deleteSTTeacher(id, teacher);
    }
}