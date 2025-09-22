package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.SubjectTeacher;
import org.spring.diaryBackend.repository.MarksRepository;
import org.spring.diaryBackend.repository.STRepository;
import org.spring.diaryBackend.service.STService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleSTService implements STService {
    private final STRepository stRepository;
    private final MarksRepository marksRepository;

    @Override
    public List<SubjectTeacher> findByAllSubjectTeacher(int offset, int limit) {
        return stRepository.findByAllSubjectTeacher(offset, limit);
    }

    @Override
    public List<SubjectTeacher> findAllSubjectTeacher() {
        return stRepository.findAll();
    }

    @Override
    public List<SubjectTeacher> findByTeacher(Long teacherId) {
        return stRepository.findByTeacher(teacherId);
    }

    @Override
    public SubjectTeacher findById(Long id) {
        return stRepository.findById(id).orElse(null);
    }

    @Override
    public SubjectTeacher saveSubjectTeacher(SubjectTeacher subjectTeacher) {
        return stRepository.save(subjectTeacher);
    }

    @Override
    public SubjectTeacher updateSubjectTeacher(SubjectTeacher subjectTeacher) {
        return stRepository.save(subjectTeacher);
    }

    @Override
    public void addingSTGroup(Long id_st, Long group) {
        stRepository.addingSTGroup(id_st, group);
        marksRepository.addSemesterMarksForGroup(group, id_st);
        marksRepository.addFirstRegularMarksForGroup(group, id_st, LocalDate.now());
    }

    @Override
    public void updateSTGroup(Long id_st, Long group, Long newGroup) {
        stRepository.updateSTGroup(id_st, group, newGroup);
    }

    @Override
    public void deleteSTGroup(Long id_st, Long group) {
        stRepository.deleteSTGroup(id_st, group);
    }

    @Override
    public void deleteSubjectTeacher(Long teacherId) {
        stRepository.deleteById(teacherId);
    }
}
