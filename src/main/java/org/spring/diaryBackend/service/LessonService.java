package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.LessonDTO;

import java.util.List;

public interface LessonService {
    List<LessonDTO> findAllLesson();

    LessonDTO addSupplement(Long id);
}
