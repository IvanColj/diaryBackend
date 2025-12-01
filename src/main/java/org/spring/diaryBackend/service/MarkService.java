package org.spring.diaryBackend.service;

import org.spring.diaryBackend.dto.entity.SemesterMarkDTO;
import org.spring.diaryBackend.dto.other.*;
import org.spring.diaryBackend.logic.DelMarksGroup;

import java.util.List;

public interface MarkService {
    List<SemesterMarkDTO> findAllMarks();
    MarksColumnDataDTO findColumnMarkInfo(Long idStudent, Long idSt, Long number);
    MarkInfoDTO findMarkInfo(Long idStudent, Long idSt, Long number);
    List<SubjectMarksInfoDTO> findByStudentAndSubject(Long idStudent, Long idSt);

    void addMarksForGroup(CRUDMarksDTO crudMarksDTO);
    SemesterMarkDTO updateMarks(SemesterMarkDTO semesterMarkDTO);
    void updateMarksNumber(UpdateMarkDTO updateMarkDTO);
    void updateCertification(SemesterMarkDTO semesterMarkDTO);
    void deleteMarksNumberGroupST(CRUDMarksDTO crudMarksDTO);
    void deleteMarksGroupSt(DelMarksGroup delMarksGroup);
}
