package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.other.StudentAllMarksDTO;

import java.util.List;


public interface StudentService {
    List<StudentDTO> findAllStudent();
    List<StudentDTO> findByIdGroup(Long group);
    List<StudentAllMarksDTO> getStudentMarks(Long id);
    StudentDTO findById(Long id);
    StudentDTO findByLoginOrPassword(String login, String password);

    void updateLeaderStatus(Long id, boolean isLeader);
    void saveStudent(StudentDTO student);
    StudentDTO updateStudent(StudentDTO studentDTO);
    void deleteStudent(Long id);
}
