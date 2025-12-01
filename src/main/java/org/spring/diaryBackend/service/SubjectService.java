package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SubjectDTO;

import java.util.List;

public interface SubjectService {
    List<SubjectDTO> findAllSubject();
    SubjectDTO findById(Long id);

    SubjectDTO saveSubject(SubjectDTO subject);
    SubjectDTO updateSubject(SubjectDTO subject);
    void deleteSubject(Long id);
}
