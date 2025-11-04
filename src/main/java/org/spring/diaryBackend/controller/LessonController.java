package org.spring.diaryBackend.controller;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.LessonDTO;
import org.spring.diaryBackend.dto.other.LessonInfoDTO;
import org.spring.diaryBackend.service.LessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping()
    public List<LessonDTO> findAll() {
        return lessonService.findAllLesson();
    }

    @GetMapping("st/{st}/group/{group}")
    public List<LessonInfoDTO> findAllLessonInfo(@PathVariable("st") Long idSt, @PathVariable("group") Long idGroup) {
        return lessonService.findByLessonInfo(idSt, idGroup);
    }

    @PostMapping("add/supplement/id/{id}")
    public LessonDTO save(@PathVariable Long id) {
        return lessonService.addSupplement(id);
    }
}
