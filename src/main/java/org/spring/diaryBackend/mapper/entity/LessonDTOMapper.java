package org.spring.diaryBackend.mapper.entity;

import org.spring.diaryBackend.dto.entity.LessonDTO;
import org.spring.diaryBackend.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LessonDTOMapper implements Function<Lesson, LessonDTO> {
    @Override
    public LessonDTO apply(Lesson lesson) {
        return new LessonDTO(
                lesson.getId(),
                lesson.getIdSchedule() != null ? lesson.getIdSchedule().getId() : null,
                lesson.getNumberWeek(),
                lesson.getIdSupplement() != null ? lesson.getIdSupplement().getId() : null,
                lesson.getDate()
        );
    }
}
