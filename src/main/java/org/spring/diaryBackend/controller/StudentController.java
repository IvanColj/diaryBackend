package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.StudentMarksDTO;
import org.spring.diaryBackend.model.Student;
import org.spring.diaryBackend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/v1/students")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
    private final StudentService service;

    @GetMapping
    public List<Student> findByAllStudents(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "5") int limit) {
        return service.findByAllStudent(offset, limit);
    }

    @GetMapping("all")
    public List<Student> findAllStudent() {
        return service.findAllStudent();
    }

    @GetMapping("id/{id}")
    public Student getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("login/{login}/password/{password}")
    public Student getByLogin(@PathVariable String login, @PathVariable String password) {
        return service.findByLoginOrPassword(login, password);
    }

    @GetMapping("group/{group}")
    public List<Student> getByGroup(@PathVariable Long group) {
        return service.findByNumberGroup(group);
    }

    @GetMapping("marks/id/{id}")
    public StudentMarksDTO getGroupMarks(@PathVariable Long id) {
        return service.getStudentMarks(id);
    }

    @PostMapping("save")
    public Student saveStudent(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    @PutMapping("update")
    public Student updateStudent(@RequestBody Student student) {
        return service.updateStudent(student);
    }

    @DeleteMapping("delete/{id}")
    public void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }
}
