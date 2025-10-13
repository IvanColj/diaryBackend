package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SemesterMarkDTO;
import org.spring.diaryBackend.dto.other.UpdateMarkDTO;
import org.spring.diaryBackend.logic.DelMarksGroup;

import java.util.List;

public interface MarkService {
    List<SemesterMarkDTO> findAllMarks();

    List<SemesterMarkDTO> findByStudentMarks(Long idStudent);

    List<SemesterMarkDTO> findByObjectMarks(Long idObject);

    void addMarksForGroup(Long idGroup, Long idSt);

    SemesterMarkDTO findByStudentAndSubject(Long idStudent, Long idSt);

    SemesterMarkDTO updateMarks(SemesterMarkDTO semesterMarkDTO);

    SemesterMarkDTO updateMarksNumber(UpdateMarkDTO updateMarkDTO);

    void deleteMarksNumberGroupST(Long idGroup, Long idSt, Long number);

    void deleteMarksGroupSt(DelMarksGroup delMarksGroup);

}
