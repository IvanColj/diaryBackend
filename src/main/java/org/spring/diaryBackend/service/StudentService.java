package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.StudentMarksDTO;
import org.spring.diaryBackend.model.Student;

import java.io.IOException;
import java.util.List;


public interface StudentService {
    List<Student> findByAllStudent(int offset, int limit);
    List<Student> findAllStudent();
    List<Student> findByNumberGroup(Long group);
    List<Student> fetchStudents(Long groupNumber) throws IOException;

    StudentMarksDTO getStudentMarks(Long id);
    Student saveStudent(Student student);
    Student updateStudent(Student student);
    Student findById(Long id);
    Student findByLoginOrPassword(String login, String password);
    void deleteStudent(Long id);
}
