package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SubjectTeacherDTO;
import org.spring.diaryBackend.dto.other.STMarkTypesDTO;
import org.spring.diaryBackend.dto.other.SubjectGroupsDTO;

import java.util.List;

public interface STService {
    List<SubjectTeacherDTO> findAllSubjectTeacher();
    List<SubjectTeacherDTO> findByTeacher(Long idTeacher);
    List<SubjectGroupsDTO> findBySTGroups(Long idTeacher);
    SubjectTeacherDTO findById(Long id);
    List<STMarkTypesDTO> findByStNumberMarkType(Long idSt);

    SubjectTeacherDTO saveSubjectTeacher(SubjectTeacherDTO subjectTeacher);
    SubjectTeacherDTO updateSubjectTeacher(SubjectTeacherDTO subjectTeacher);
    void addingSTGroup(Long idSt, Long idGroup);
    void addingTeacher(Long idSt, Long idTeacher);
    void deleteSTGroup(Long idSt, Long idGroup);
    void deleteSTTeacher(Long idSt, Long idTeacher);
    void deleteSubjectTeacher(Long idTeacher);
}
