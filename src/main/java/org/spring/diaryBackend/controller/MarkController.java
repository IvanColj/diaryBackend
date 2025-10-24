package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.SemesterMarkDTO;
import org.spring.diaryBackend.dto.other.CRUDMarksDTO;
import org.spring.diaryBackend.dto.other.ColumnMarkDTO;
import org.spring.diaryBackend.dto.other.SubjectMarksDTO;
import org.spring.diaryBackend.dto.other.UpdateMarkDTO;
import org.spring.diaryBackend.service.MarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/marks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MarkController {
    private final MarkService markService;

    @GetMapping
    public List<SemesterMarkDTO> getAllMarks() {
        return markService.findAllMarks();
    }

    @GetMapping("student/{id_student}/subject/{id_st}")
    public List<SubjectMarksDTO> getMarksStudentsSubject(@PathVariable Long id_student, @PathVariable Long id_st) {
        return markService.findByStudentAndSubject(id_student, id_st);
    }

    @GetMapping("info/student/{idStudent}/st/{idSt}/number/{number}")
    public ColumnMarkDTO findColumnMarkInfo(@PathVariable Long idStudent, @PathVariable Long idSt, @PathVariable Long number) {
        return markService.findColumnMarkInfo(idStudent, idSt, number);
    }

//    @DeleteMapping("delete/group")
//    public void deleteMarksNumber(@RequestBody DelMarksGroup delMarksGroup) {
//        markService.deleteMarksGroupSt(delMarksGroup);
//    }

    @DeleteMapping("delete/group")
    public void deleteMarksNumberGroupST(@RequestBody CRUDMarksDTO crudMarksDTO) {
        markService.deleteMarksNumberGroupST(crudMarksDTO);
    }

    @PatchMapping("update")
    public SemesterMarkDTO updateMarks(@RequestBody SemesterMarkDTO semesterMarks) {
        return markService.updateMarks(semesterMarks);
    }

    @PatchMapping ("updateOneMark")
    public void updateMarksNumber(@RequestBody UpdateMarkDTO updateMarkDTO) {
        markService.updateMarksNumber(updateMarkDTO);
    }

    @PostMapping("save/group")
    public void saveMarksGroup(@RequestBody CRUDMarksDTO crudMarksDTO) {
        markService.addMarksForGroup(crudMarksDTO);
    }
}
