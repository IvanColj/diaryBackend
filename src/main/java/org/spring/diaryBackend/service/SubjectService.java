package org.spring.diaryBackend.service;

import org.spring.diaryBackend.model.Subject;
import java.util.List;

public interface SubjectService {
    List<Subject> findByAllSubject(int offset, int limit);
    List<Subject> findAllSubject();
    Subject findById(Long id);
    Subject saveSubject(Subject subject);
    Subject updateSubject(Subject subject);
    void deleteSubject(Long id);
}
