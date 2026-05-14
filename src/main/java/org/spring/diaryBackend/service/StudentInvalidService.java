package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentInvalidDTO;

import java.util.List;

public interface StudentInvalidService {
    List<StudentInvalidDTO> findAll();
    StudentInvalidDTO save(StudentInvalidDTO dto);
    StudentInvalidDTO update(StudentInvalidDTO dto);
    void delete(Long id);
}
