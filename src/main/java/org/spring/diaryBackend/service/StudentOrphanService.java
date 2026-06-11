package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentOrphanDTO;
import org.spring.diaryBackend.dto.other.ExportStudentOrphanDTO;

import java.util.List;

public interface StudentOrphanService {
    List<ExportStudentOrphanDTO> findAll();
    StudentOrphanDTO save(StudentOrphanDTO dto);
    StudentOrphanDTO update(StudentOrphanDTO dto);
    void delete(Long id);
}
