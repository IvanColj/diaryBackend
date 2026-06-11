package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentInvalidDTO;
import org.spring.diaryBackend.dto.other.ExportStudentInvalidDTO;

import java.util.List;

public interface StudentInvalidService {
    List<ExportStudentInvalidDTO> findAll();
    StudentInvalidDTO save(StudentInvalidDTO dto);
    StudentInvalidDTO update(StudentInvalidDTO dto);
    void delete(Long id);
}
