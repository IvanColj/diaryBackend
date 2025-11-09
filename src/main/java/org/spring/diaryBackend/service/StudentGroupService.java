package org.spring.diaryBackend.service;

//import org.spring.diaryBackend.dto.GroupMarksDTO;
//import org.spring.diaryBackend.dto.other.STNameSubjectDTO;

import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.entity.StudentGroupDTO;
import org.spring.diaryBackend.dto.other.GroupMarksDTO;
import org.spring.diaryBackend.dto.other.NameSubjectTeachersDTO;

import java.io.IOException;
import java.util.List;

public interface StudentGroupService {

    List<StudentGroupDTO> findAll();
    List<GroupMarksDTO> getGroupMarksBySubject(Long idGroup, Long idSt, Long idTeacher);
    List<NameSubjectTeachersDTO> findBySubject(Long group);
    List<StudentDTO> fetchStudentsGroup(Long groupNumber) throws IOException;

    StudentGroupDTO findStudentGroupByNumberGroupAndAdmissionYear(Long numberGroup, Long admissionYear);
    List<StudentGroupDTO> findStudentGroupByNumberGroup(Long numberGroup);
    StudentGroupDTO findStudentGroupByIdGroup(Long numberGroup);
    StudentGroupDTO updateGroup(StudentGroupDTO group);
    void deleteIdGroup(Long numberGroup);
}
