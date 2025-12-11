package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.LessonDTO;
import org.spring.diaryBackend.dto.other.LessonDateDTO;
import org.spring.diaryBackend.dto.other.LessonInfoDTO;

import java.util.List;

public interface LessonService {
    List<LessonDTO> findAllLesson();
    List<LessonInfoDTO> findByLessonInfo(Long idSt, Long idGroup, Long idTeacher);
    List<LessonDateDTO> findByLessonSubject(Long idSt, Long idGroup, Long idTeacher);

    LessonDTO addSupplement(Long id);
}
