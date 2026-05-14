package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentOrphanDTO;

import java.util.List;

public interface StudentOrphanService {
    List<StudentOrphanDTO> findAll();
    StudentOrphanDTO save(StudentOrphanDTO dto);
    StudentOrphanDTO update(StudentOrphanDTO dto);
    void delete(Long id);
}
