package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.other.StudentAllMarksDTO;
import org.spring.diaryBackend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<StudentDTO> findAllStudent() {
        return studentService.findAllStudent();
    }

    @GetMapping("id/{id}")
    public StudentDTO getById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @GetMapping("login/{login}/password/{password}")
    public StudentDTO getByLogin(@PathVariable String login,
                                 @PathVariable String password) {
        return studentService.findByLoginOrPassword(login, password);
    }

    @GetMapping("group/{group}")
    public List<StudentDTO> getByGroup(@PathVariable Long group) {
        return studentService.findByIdGroup(group);
    }

    @GetMapping("marks/id/{id}")
    public List<StudentAllMarksDTO> getGroupMarks(@PathVariable Long id) {
        return studentService.getStudentMarks(id);
    }

    @PatchMapping("leader/{id}")
    public void updateLeaderStatus(@PathVariable Long id, @RequestParam("status") Boolean status) {
        studentService.updateLeaderStatus(id, status);
    }

    @PatchMapping("update")
    public StudentDTO updateStudent(@RequestBody StudentDTO student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("delete/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}