package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.SemesterMarks;
import org.spring.diaryBackend.service.MarksService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/marks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MarksController {
    private final MarksService service;

    @GetMapping
    public List<SemesterMarks> findByAllMarks(
        @RequestParam(required = false, defaultValue = "0") int offset,
        @RequestParam(required = false, defaultValue = "5") int limit) {
            return service.findByAllMarks(offset, limit);
    }

    @GetMapping("all")
    public List<SemesterMarks> getAllMarks() {
        return service.findAllMarks();
    }

    @GetMapping("student/{id_student}")
    public List<SemesterMarks> getMarksStudents(@PathVariable Long id_student) {
        return service.findByStudentMarks(id_student);
    }

    @GetMapping("subject/{id_st}")
    public List<SemesterMarks> getMarksObject(@PathVariable Long id_st) {
        return service.findByObjectMarks(id_st);
    }

    @GetMapping("student/{id_student}/subject/{id_st}")
    public SemesterMarks getMarksStudentsSubject(@PathVariable Long id_student, @PathVariable Long id_st) {
        return service.findByStudentAndSubject(id_student, id_st);
    }

    @DeleteMapping("delete/student/{id_student}/subject/{id_st}")
    public void deleteMarks(@PathVariable Long id_student, @PathVariable Long id_st) {
        service.deleteMarks(id_student, id_st);
    }

    @DeleteMapping("delete/student/{id_student}/subject/{id_st}/offset/{offset}")
    public void deleteMarksNumber(@PathVariable Long id_student, @PathVariable Long id_st, @PathVariable Long offset) {
        service.deleteMarksNumber(id_student, id_st, offset);
    }

    @PostMapping("save")
    public SemesterMarks saveMarks(@RequestBody SemesterMarks semesterMarks) {
        return service.saveMarks(semesterMarks);
    }

    @PostMapping("save/student/{id_student}/subject/{id_st}/marks/{marks}")
    public void saveMarksMarks(@PathVariable Long id_student, @PathVariable Long id_st, @PathVariable double marks) {
        service.saveMark(id_student, id_st, marks);
    }

    @PatchMapping("update")
    public SemesterMarks updateMarks(@RequestBody SemesterMarks semesterMarks) {
        return service.updateMarks(semesterMarks);
    }

    @PatchMapping ("update/student/{id_student}/subject/{id_st}/marks/{marks}/number/{number}")
    public SemesterMarks updateMarksNumber(@PathVariable Long id_student, @PathVariable Long id_st, @PathVariable double marks, @PathVariable int number) {
        return service.updateMarksNumber(id_student, id_st, marks, number);
    }

    @PostMapping("save/group/{group}/st/{st}")
    public void saveMarksGroup(@PathVariable Long group, @PathVariable Long st) {
        service.addMarksForGroup(group, st);
    }
}
