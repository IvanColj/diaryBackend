package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.dto.entity.LessonDTO;
import org.spring.diaryBackend.dto.entity.SupplementDTO;
import org.spring.diaryBackend.dto.other.LessonDateDTO;
import org.spring.diaryBackend.dto.other.LessonInfoDTO;
import org.spring.diaryBackend.mapper.entity.LessonDTOMapper;
import org.spring.diaryBackend.model.Lesson;
import org.spring.diaryBackend.model.Supplement;
import org.spring.diaryBackend.repository.LessonRepository;
import org.spring.diaryBackend.repository.SupplementRepository;
import org.spring.diaryBackend.service.LessonService;
import org.spring.diaryBackend.service.SupplementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SimpleLessonService implements LessonService {
    private final LessonRepository lessonRepository;
    private final SupplementRepository supplementRepository;
    private final SupplementService supplementService;
    private final LessonDTOMapper lessonDTOMapper;

    @Override
    public List<LessonDTO> findAllLesson() {
        return lessonRepository.findAll()
                .stream()
                .map(lessonDTOMapper)
                .toList();
    }

    @Override
    public LessonDTO addSupplement(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        SupplementDTO supplementDTO = supplementService.save();
        Supplement supplement = supplementRepository.findById(supplementDTO.getId()).orElse(null);
        Objects.requireNonNull(lesson).setIdSupplement(supplement);
        lessonRepository.save(lesson);
        return lessonDTOMapper.apply(lesson);
    }

    @Override
    public List<LessonInfoDTO> findByLessonInfo(Long idSt, Long idGroup, Long idTeacher) {
        return lessonRepository.findLessonInfo(idSt, idGroup, idTeacher);
    }

    @Override
    public List<LessonDateDTO> findByLessonSubject(Long idSt, Long idGroup, Long idTeacher) {
        return lessonRepository.findLessonDates(idSt, idGroup, idTeacher);
    }
}