package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.LessonDTO;
import org.spring.diaryBackend.dto.other.LessonInfoDTO;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    List<LessonDTO> findAllLesson();

    LessonDTO addSupplement(Long id);

    List<LessonInfoDTO> findByLessonInfo(Long idSt, Long idGroup);

    List<LocalDate> findByLessonSubject(Long idSt, Long idGroup);
}
