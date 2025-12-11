package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.ChangeDTO;
import org.spring.diaryBackend.dto.other.MarkChangeDTO;
import org.spring.diaryBackend.service.ChangeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/changes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChangeController {
    private final ChangeService changeService;

    @GetMapping
    public List<ChangeDTO> findAll() {
        return changeService.findAllChange();
    }

    @GetMapping("mark/st/{idSt}/student/{idStudent}/number/{number}")
    public List<MarkChangeDTO> findAllChangeMark(@PathVariable Long idSt,
                                                 @PathVariable Long idStudent,
                                                 @PathVariable Long number) {
        return changeService.findByAllChangeMark(idSt, idStudent, number);
    }

    @PostMapping("add/supplement/id/{id}")
    public ChangeDTO save(@PathVariable Long id) {
        return changeService.addSupplement(id);
    }

    @PostMapping("add/student/st/{idSt}/student/{idStudent}/number/{number}")
    public ChangeDTO saveStudent(@PathVariable Long idSt,
                                 @PathVariable Long idStudent,
                                 @PathVariable Long number) {
        return changeService.saveStudent(idSt, idStudent, number);
    }

    @PostMapping("add/teacher/st/{idSt}/student/{idStudent}/number/{number}")
    public ChangeDTO saveTeacher(@PathVariable Long idSt,
                                 @PathVariable Long idStudent,
                                 @PathVariable Long number) {
        return changeService.saveTeacher(idSt, idStudent, number);
    }
}