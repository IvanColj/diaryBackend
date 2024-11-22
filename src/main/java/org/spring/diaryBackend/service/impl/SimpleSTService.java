package org.spring.diaryBackend.service.impl;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.SubjectTeacher;
import org.spring.diaryBackend.repository.STRepository;
import org.spring.diaryBackend.service.STService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleSTService implements STService {
    STRepository repository;

    @Override
    public List<SubjectTeacher> findByAllSubjectTeacher(int offset, int limit) {
        return repository.findByAllSubjectTeacher(offset, limit);
    }

    @Override
    public List<SubjectTeacher> findAllSubjectTeacher() {
        return repository.findAll();
    }

    @Override
    public List<SubjectTeacher> findByTeacher(Long teacherId) {
        return repository.findByTeacher(teacherId);
    }

    @Override
    public SubjectTeacher findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public SubjectTeacher saveSubjectTeacher(SubjectTeacher subjectTeacher) {
        return repository.save(subjectTeacher);
    }

    @Override
    public SubjectTeacher updateSubjectTeacher(SubjectTeacher subjectTeacher) {
        return repository.save(subjectTeacher);
    }

    @Override
    public void addingSTGroup(Long id_st, Long group) {
        repository.addingSTGroup(id_st, group);
    }

    @Override
    public void updateSTGroup(Long id_st, Long group, Long newGroup) {
        repository.updateSTGroup(id_st, group, newGroup);
    }

    @Override
    public void deleteSTGroup(Long id_st, Long group) {
        repository.deleteSTGroup(id_st, group);
    }

    @Override
    public void deleteSubjectTeacher(Long teacherId) {
        repository.deleteById(teacherId);
    }
}
