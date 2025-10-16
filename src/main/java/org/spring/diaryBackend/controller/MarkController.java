package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SemesterMarkDTO;
import org.spring.diaryBackend.dto.other.SubjectMarksDTO;
import org.spring.diaryBackend.dto.other.UpdateMarkDTO;
import org.spring.diaryBackend.logic.DelMarksGroup;
import org.spring.diaryBackend.service.MarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/marks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MarkController {
    private final MarkService service;

    @GetMapping
    public List<SemesterMarkDTO> getAllMarks() {
        return service.findAllMarks();
    }

    @GetMapping("student/{id_student}")
    public List<SemesterMarkDTO> getMarksStudents(@PathVariable Long id_student) {
        return service.findByStudentMarks(id_student);
    }

    @GetMapping("subject/{id_st}")
    public List<SemesterMarkDTO> getMarksObject(@PathVariable Long id_st) {
        return service.SubjectMarksDTO(id_st);
    }

    @GetMapping("student/{id_student}/subject/{id_st}")
    public List<SubjectMarksDTO> getMarksStudentsSubject(@PathVariable Long id_student, @PathVariable Long id_st) {
        return service.findByStudentAndSubject(id_student, id_st);
    }

    @DeleteMapping("delete/group")
    public void deleteMarksNumber(@RequestBody DelMarksGroup delMarksGroup) {
        service.deleteMarksGroupSt(delMarksGroup);
    }

    @DeleteMapping("delete/group/{idGroup}/st/{idSt}/number/{number}")
    public void deleteMarksNumberGroupST(@PathVariable Long idGroup, @PathVariable Long idSt, @PathVariable Long number) {
        service.deleteMarksNumberGroupST(idGroup, idSt, number);
    }

    @PatchMapping("update")
    public SemesterMarkDTO updateMarks(@RequestBody SemesterMarkDTO semesterMarks) {
        return service.updateMarks(semesterMarks);
    }

    @PatchMapping ("updateOneMark")
    public void updateMarksNumber(@RequestBody UpdateMarkDTO updateMarkDTO) {
        service.updateMarksNumber(updateMarkDTO);
    }

    @PostMapping("save/group/{group}/st/{st}")
    public void saveMarksGroup(@PathVariable Long group, @PathVariable Long st) {
        service.addMarksForGroup(group, st);
    }
}
