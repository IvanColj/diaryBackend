package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.entity.StudentGroupDTO;
import org.spring.diaryBackend.dto.other.GroupMarksDTO;
import org.spring.diaryBackend.dto.other.GroupReportDTO;
import org.spring.diaryBackend.dto.other.STTeachersDTO;
import org.spring.diaryBackend.dto.other.StudentCategoryGroupDTO;

import java.io.IOException;
import java.util.List;

public interface StudentGroupService {
    List<StudentGroupDTO> findAll();
    List<GroupMarksDTO> getGroupMarksBySubject(Long idGroup, Long idSt, Long idTeacher);
    List<STTeachersDTO> findBySubject(Long group);
    List<StudentDTO> fetchStudentsGroup(Long groupNumber) throws IOException;
    List<StudentGroupDTO> findStudentGroupByNumberGroup(Long numberGroup);
    StudentGroupDTO findStudentGroupByNumberGroupAndAdmissionYear(Long numberGroup, Long admissionYear);
    StudentGroupDTO findStudentGroupByIdGroup(Long numberGroup);

    GroupReportDTO getFullGroupReport(Long groupId);
    List<StudentCategoryGroupDTO> getStudentsByScholarshipCategory(Long groupId);

    StudentGroupDTO updateGroup(StudentGroupDTO group);
    void deleteIdGroup(Long numberGroup);
}
