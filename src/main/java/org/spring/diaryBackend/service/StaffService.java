package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StaffDTO;
import org.spring.diaryBackend.dto.entity.SubjectDTO;
import org.spring.diaryBackend.dto.other.SubjectCourseDTO;
import org.spring.diaryBackend.dto.other.SubjectGroupDTO;

import java.util.List;

public interface StaffService {
    List<StaffDTO> findAllStaff();

    List<SubjectDTO> findByAllSubject(Long id);

    List<SubjectCourseDTO> findBySubjectCourse(Long idTeacher);

    List<SubjectGroupDTO> findByGroup(Long idTeacher);

    StaffDTO findStaffById(Long id);

    StaffDTO saveStaff(StaffDTO staffDTO);

    StaffDTO updateStaff(StaffDTO staff);

    void addStaffJob(Long idStaff, Long idJob);

    void deleteStaffJob(Long idStaff, Long idJob);

    StaffDTO findByLoginOrPassword(String login, String password);

    void deleteStaff(Long id);
}
