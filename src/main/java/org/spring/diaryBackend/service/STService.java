package org.spring.diaryBackend.service;

import org.spring.diaryBackend.model.SubjectTeacher;

import java.util.List;

public interface STService {
    List<SubjectTeacher> findByAllSubjectTeacher(int offset, int limit);
    List<SubjectTeacher> findAllSubjectTeacher();
    List<SubjectTeacher> findByTeacher(Long teacherId);

    SubjectTeacher findById(Long id);
    SubjectTeacher saveSubjectTeacher(SubjectTeacher subjectTeacher);
    SubjectTeacher updateSubjectTeacher(SubjectTeacher subjectTeacher);
    void addingSTGroup(Long id_st, Long group);
    void updateSTGroup(Long id_st, Long group, Long newGroup);
    void deleteSTGroup(Long id_st, Long group);
    void deleteSubjectTeacher(Long teacherId);
}
