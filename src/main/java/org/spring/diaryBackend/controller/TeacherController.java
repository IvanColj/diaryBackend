package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.Subject;
import org.spring.diaryBackend.model.Teacher;
import org.spring.diaryBackend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teachers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeacherController {
    private final TeacherService service;

    @GetMapping
    public List<Teacher> findByAllTeacher(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "5") int limit) {
        return service.findByAllTeacher(offset, limit);
    }

    @GetMapping("all")
    public List<Teacher> findAllTeacher() {
        return service.findAllTeacher();
    }

    @GetMapping("id/{id}")
    public Teacher findById(@PathVariable Long id) {
        return service.findTeacherById(id);
    }

    @GetMapping("login/{login}/password/{password}")
    public Teacher getByLogin(@PathVariable String login, @PathVariable String password) {
        return service.findByLoginOrPassword(login, password);
    }

    @GetMapping("subjects/{id}")
    public List<Subject> findByAllSubject(@PathVariable Long id) {
        return service.findByAllSubject(id);
    }

    @PostMapping("save")
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return service.saveTeacher(teacher);
    }

    @PatchMapping("update")
    public Teacher updateTeacher(@RequestBody Teacher teacher) {
        return service.updateTeacher(teacher);
    }

    @DeleteMapping("delete/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        service.deleteTeacher(id);
    }
}
