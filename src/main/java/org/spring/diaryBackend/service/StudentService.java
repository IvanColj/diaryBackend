package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.other.StudentMarksAllSubjectDTO;

import java.util.List;


public interface StudentService {
    List<StudentDTO> findAllStudent();
    List<StudentDTO> findByIdGroup(Long group);

    List<StudentMarksAllSubjectDTO> getStudentMarks(Long id);
    void saveStudent(StudentDTO student);
    StudentDTO updateStudent(StudentDTO studentDTO);
    StudentDTO findById(Long id);
    StudentDTO findByLoginOrPassword(String login, String password);
    void deleteStudent(Long id);
}
