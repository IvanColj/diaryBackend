package org.spring.diaryBackend.controller;

import lombok.RequiredArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.entity.StudentGroupDTO;
import org.spring.diaryBackend.dto.other.GroupMarksDTO;
import org.spring.diaryBackend.dto.other.STNameSubjectDTO;
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

    @GetMapping("marks/group/{group}/subject/{subject}")
    public List<GroupMarksDTO> findGroupMarks(@PathVariable Long group, @PathVariable Long subject) {
        return studentGroupService.getGroupMarksBySubject(group, subject);
    }

    @GetMapping("subjects/group/{group}")
    public List<STNameSubjectDTO> getBySubject(@PathVariable Long group) {
        return studentGroupService.findBySubject(group);
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
