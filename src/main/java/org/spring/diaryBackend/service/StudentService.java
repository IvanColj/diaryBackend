package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StudentDTO;
import org.spring.diaryBackend.dto.other.CourseStatsDTO;
import org.spring.diaryBackend.dto.other.OverallStatsDTO;
import org.spring.diaryBackend.dto.other.StudentAllMarksDTO;
import org.spring.diaryBackend.dto.other.StudentLeaderDTO;
import org.spring.diaryBackend.dto.other.GroupPerformanceDTO;

import java.util.List;


public interface StudentService {
    List<StudentDTO> findAllStudent();
    List<StudentDTO> findByIdGroup(Long group);
    List<StudentAllMarksDTO> getStudentMarks(Long id);
    OverallStatsDTO getOverallStats();
    List<CourseStatsDTO> getStatsByCourse();
    StudentDTO findById(Long id);
    List<StudentLeaderDTO> getGroupLeaders(Long groupId);
    StudentDTO findByLoginOrPassword(String login, String password);

    void updateLeaderStatus(Long id, boolean isLeader);
    void saveStudent(StudentDTO student);
    StudentDTO updateStudent(StudentDTO studentDTO);
    void deleteStudent(Long id);

    List<GroupPerformanceDTO> getGroupPerformance(Long groupId);
    org.spring.diaryBackend.dto.other.StudentDetailsDTO getStudentDetails(Long studentId);
}
