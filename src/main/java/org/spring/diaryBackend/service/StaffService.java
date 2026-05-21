package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.StaffDTO;
import org.spring.diaryBackend.dto.entity.SubjectDTO;
import org.spring.diaryBackend.dto.other.CourseSubjectsDTO;
import org.spring.diaryBackend.dto.other.GroupSubjectsDTO;

import java.util.List;

public interface StaffService {
    List<StaffDTO> findAllStaff();
    List<SubjectDTO> findByAllSubject(Long id);
    List<CourseSubjectsDTO> findBySubjectCourse(Long idTeacher);
    List<GroupSubjectsDTO> findByGroup(Long idTeacher);
    StaffDTO findStaffById(Long id);
    StaffDTO findByLoginOrPassword(String login, String password);
    String getStaffNote(Long id);

    StaffDTO saveStaff(StaffDTO staffDTO);
    StaffDTO updateStaff(StaffDTO staff);
    void addStaffJob(Long idStaff, Long idJob);
    void deleteStaffJob(Long idStaff, Long idJob);
    void deleteStaff(Long id);
}
