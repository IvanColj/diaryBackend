package org.spring.diaryBackend.controller;

import lombok.RequiredArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.entity.StudentGroupDTO;
import org.spring.diaryBackend.dto.other.GroupMarksDTO;
import org.spring.diaryBackend.dto.other.GroupReportDTO;
import org.spring.diaryBackend.dto.other.STTeachersDTO;
import org.spring.diaryBackend.dto.other.StudentCategoryGroupDTO;
import org.spring.diaryBackend.service.StudentGroupService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class StudentGroupController {
    private final StudentGroupService studentGroupService;

    @GetMapping
    public List<StudentGroupDTO> getAllGroups() {
        return studentGroupService.findAll();
    }

    @GetMapping("id/{idGroup}")
    public StudentGroupDTO getById(@PathVariable Long idGroup) {
        return studentGroupService.findStudentGroupByIdGroup(idGroup);
    }

    @GetMapping("number/{groupNumber}")
    public List<StudentGroupDTO> findStudentGroupByNumberGroup(@PathVariable Long groupNumber) {
        return studentGroupService.findStudentGroupByNumberGroup(groupNumber);
    }

    @GetMapping("add/{groupNumber}")
    public List<StudentDTO> fetchStudents(@PathVariable Long groupNumber) throws IOException {
        return studentGroupService.fetchStudentsGroup(groupNumber);
    }

    @GetMapping("marks/group")
    public List<GroupMarksDTO> findGroupMarks(
            @RequestParam(value = "idGroup") Long idGroup,
            @RequestParam(value = "idSt") Long idSt,
            @RequestParam(value = "idTeacher") Long idTeacher) {

        return studentGroupService.getGroupMarksBySubject(idGroup, idSt, idTeacher);
    }

    @GetMapping("subjects/group/{group}")
    public List<STTeachersDTO> getBySubject(@PathVariable Long group) {
        return studentGroupService.findBySubject(group);
    }

    @GetMapping("report/{groupId}")
    public GroupReportDTO getGroupReport(@PathVariable Long groupId) {
        return studentGroupService.getFullGroupReport(groupId);
    }

    @GetMapping("categories/{groupId}")
    public List<StudentCategoryGroupDTO> getStudentsByCategory(@PathVariable Long groupId) {
        return studentGroupService.getStudentsByScholarshipCategory(groupId);
    }

    @PatchMapping("update")
    public StudentGroupDTO updateGroup(@RequestBody StudentGroupDTO studentGroup) {
        return studentGroupService.updateGroup(studentGroup);
    }

    @DeleteMapping("delete/{idGroup}")
    public void deleteIdGroup(@PathVariable Long idGroup) {
        studentGroupService.deleteIdGroup(idGroup);
    }
}