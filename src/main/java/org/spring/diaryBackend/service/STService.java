package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SubjectTeacherDTO;
import org.spring.diaryBackend.dto.other.STGroupsDTO;
import org.spring.diaryBackend.dto.other.STNumberMarkTypeMarkDTO;

import java.util.List;

public interface STService {
    List<SubjectTeacherDTO> findAllSubjectTeacher();

    List<SubjectTeacherDTO> findByTeacher(Long idTeacher);

    List<STGroupsDTO> findBySTGroups(Long idTeacher);

    SubjectTeacherDTO findById(Long id);

    List<STNumberMarkTypeMarkDTO> findByStNumberMarkType(Long idSt);

    SubjectTeacherDTO saveSubjectTeacher(SubjectTeacherDTO subjectTeacher);

    SubjectTeacherDTO updateSubjectTeacher(SubjectTeacherDTO subjectTeacher);

    void addingSTGroup(Long idSt, Long idGroup);

    void deleteSTGroup(Long idSt, Long idGroup);

    void deleteSubjectTeacher(Long idTeacher);
}
