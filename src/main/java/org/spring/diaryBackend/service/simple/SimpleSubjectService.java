package org.spring.diaryBackend.service.simple;

import lombok.AllArgsConstructor;
import org.spring.diaryBackend.model.Subject;
import org.spring.diaryBackend.repository.SubjectRepository;
import org.spring.diaryBackend.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleSubjectService implements SubjectService {
    private final SubjectRepository repository;

    @Override
    public List<Subject> findByAllSubject(int offset, int limit) {
        return repository.findByAllSubject(offset, limit);
    }

    @Override
    public List<Subject> findAllSubject() {
        return repository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        Optional<Subject> subject = repository.findById(id);
        return subject.orElse(null);
    }

    @Override
    public Subject saveSubject(Subject subject) {
        return repository.save(subject);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        return repository.save(subject);
    }

    @Override
    public void deleteSubject(Long id) {
        repository.deleteById(id);
    }
}
